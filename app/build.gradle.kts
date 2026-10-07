package com.example.data

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialCancellationException
import com.example.R
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID

data class UserSession(
    val userId: String,
    val displayName: String,
    val emailOrId: String,
    val authProvider: String, // "google" or "student_credentials"
    val isLoggedIn: Boolean
)

sealed interface AuthResult {
    data class Success(val user: UserSession) : AuthResult
    data class Error(val message: String) : AuthResult
    data object Cancelled : AuthResult
}

class AuthManager(private val context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        AUTH_PREFS_NAME,
        Context.MODE_PRIVATE
    )

    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val credentialManager: CredentialManager by lazy { CredentialManager.create(context) }

    private val _currentUser = MutableStateFlow(loadSavedSession())
    val currentUser: StateFlow<UserSession?> = _currentUser.asStateFlow()

    private fun loadSavedSession(): UserSession? {
        val uid = prefs.getString("user_id", null) ?: return null
        val name = prefs.getString("display_name", "बिहार परीक्षार्थी") ?: "बिहार परीक्षार्थी"
        val emailOrId = prefs.getString("email_or_id", "") ?: ""
        val provider = prefs.getString("provider", "google") ?: "google"
        return UserSession(
            userId = uid,
            displayName = name,
            emailOrId = emailOrId,
            authProvider = provider,
            isLoggedIn = true
        )
    }

    private fun saveSession(session: UserSession) {
        prefs.edit()
            .putString("user_id", session.userId)
            .putString("display_name", session.displayName)
            .putString("email_or_id", session.emailOrId)
            .putString("provider", session.authProvider)
            .apply()
        _currentUser.value = session
    }

    fun clearSession() {
        prefs.edit().clear().apply()
        try {
            auth.signOut()
        } catch (e: Exception) {
            Log.w(TAG, "Error signing out from FirebaseAuth: ${e.message}")
        }
        _currentUser.value = null
    }

    private fun getConfiguredGoogleClientId(): String? {
        val value = context.getString(R.string.default_web_client_id)
            .trim()
            .replace("\"", "")
        return value.takeIf { it.isNotEmpty() && it != "null" }
    }

    /**
     * Interactive Google Sign-In via Jetpack CredentialManager.
     * Uses GetSignInWithGoogleOption with the configured default_web_client_id.
     */
    suspend fun signInWithGoogle(): AuthResult = withContext(Dispatchers.Main) {
        try {
            val webClientId = getConfiguredGoogleClientId()
            if (webClientId.isNullOrBlank()) {
                Log.e(TAG, "Google Sign-In is not configured. Missing default_web_client_id. Add google-services.json and sync the project.")
                return@withContext AuthResult.Error(
                    "Google Sign-In अभी कॉन्फ़िगर नहीं है। Firebase में Android ऐप जोड़ें, app/google-services.json डालें और project sync करें."
                )
            }

            val rawNonce = UUID.randomUUID().toString()
            val md = MessageDigest.getInstance("SHA-256")
            val digest = md.digest(rawNonce.toByteArray())
            val hashedNonce = digest.fold("") { str, it -> str + "%02x".format(it) }

            val googleIdOption = GetSignInWithGoogleOption.Builder(webClientId)
                .setNonce(hashedNonce)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val result = credentialManager.getCredential(
                request = request,
                context = context
            )

            val credential = result.credential
            if (credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                val idToken = googleIdTokenCredential.idToken

                val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)
                val authResult = auth.signInWithCredential(firebaseCred).await()
                val firebaseUser = authResult.user

                if (firebaseUser != null) {
                    val session = UserSession(
                        userId = firebaseUser.uid,
                        displayName = firebaseUser.displayName ?: googleIdTokenCredential.displayName ?: "बिहार परीक्षार्थी",
                        emailOrId = firebaseUser.email ?: googleIdTokenCredential.id,
                        authProvider = "google",
                        isLoggedIn = true
                    )
                    saveSession(session)
                    return@withContext AuthResult.Success(session)
                }
            }

            AuthResult.Error("Google क्रेडेंशियल सत्यापित नहीं किया जा सका।")
        } catch (e: GetCredentialCancellationException) {
            Log.d(TAG, "User cancelled Google Sign-in flow.")
            AuthResult.Cancelled
        } catch (e: Exception) {
            Log.e(TAG, "Google Sign-in exception: ${e.message}", e)
            AuthResult.Error(e.localizedMessage ?: "Google से लॉगिन विफल रहा।")
        }
    }

    /**
     * Sign in or Register using Student ID and Password.
     * Enables students without Google Accounts to seamlessly sync their progress across devices.
     */
    suspend fun signInWithStudentCredentials(
        studentId: String,
        password: String,
        isRegister: Boolean
    ): AuthResult = withContext(Dispatchers.IO) {
        val cleanId = studentId.trim().lowercase()
        val cleanPass = password.trim()

        if (cleanId.length < 3) {
            return@withContext AuthResult.Error("कृपया वैध छात्र ID (कम से कम 3 अक्षर) दर्ज करें।")
        }
        if (cleanPass.length < 4) {
            return@withContext AuthResult.Error("पासवर्ड कम से कम 4 अक्षरों का होना चाहिए।")
        }

        val passHash = hashString(cleanPass)
        val studentKey = "student_acc_${cleanId}"
        val savedHash = prefs.getString(studentKey, null)

        if (isRegister) {
            // Check if already registered with a different password
            if (savedHash != null && savedHash != passHash) {
                return@withContext AuthResult.Error("यह छात्र ID पहले से पंजीकृत है। कृपया सही पासवर्ड से लॉगिन करें।")
            }
            prefs.edit().putString(studentKey, passHash).apply()
        } else {
            // Login check: if already registered on this device, verify password
            if (savedHash != null && savedHash != passHash) {
                return@withContext AuthResult.Error("गलत पासवर्ड! कृपया सही पासवर्ड दर्ज करें।")
            }
            if (savedHash == null) {
                // New device login with ID & password: register credential locally
                prefs.edit().putString(studentKey, passHash).apply()
            }
        }

        // Generate deterministic secure UID for this student ID
        val deterministicUid = "student_${hashString(cleanId).take(20)}"
        val displayName = "छात्र (${studentId.trim()})"

        val session = UserSession(
            userId = deterministicUid,
            displayName = displayName,
            emailOrId = cleanId,
            authProvider = "student_credentials",
            isLoggedIn = true
        )
        saveSession(session)
        AuthResult.Success(session)
    }

    private fun hashString(input: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        return md.digest(input.toByteArray()).fold("") { str, it -> str + "%02x".format(it) }
    }

    companion object {
        private const val TAG = "AuthManager"
        private const val AUTH_PREFS_NAME = "blet_auth_session_v1"
    }
}
