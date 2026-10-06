# Firebase Setup Guide for Bihar Librarian Quiz App

## Overview
This app uses Firebase for:
- **Firebase Authentication** (Google Sign-In + Student ID/Password)
- **Firestore Database** (Cloud progress sync across devices)
- **Firebase App Check** (Security for API calls)

---

## Step 1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click **"Create a project"** or use existing one
3. Project name: **Bihar Librarian Quiz** (or your choice)
4. Accept the terms and create

---

## Step 2: Register Android App in Firebase

1. In Firebase Console, click **"Add app"** → **Android**
2. Fill in the form:
   - **Package name:** `com.aistudio.biharlibrarian.vqzq`
   - **App nickname:** Bihar Librarian Quiz
   - **Debug signing certificate SHA-1:** (See Step 3)

### Get Debug SHA-1 Certificate Fingerprint

Run this command in your project root:

```bash
# On macOS/Linux:
./gradlew signingReport

# On Windows:
gradlew.bat signingReport
```

Look for output like:
```
Variant: debugUnsignedConfig
Config: debugConfig
  MD5: XX:XX:XX:XX...
  SHA1: AA:BB:CC:DD:EE:FF:00:11:22:33:44:55:66:77:88:99:AA:BB:CC:DD
  SHA-256: ...
```

Copy the **SHA1** value and paste it into Firebase Console.

3. Click **"Register app"**
4. Firebase will generate `google-services.json` → **Download it**

---

## Step 3: Download google-services.json

1. After registering the app, Firebase shows a download button
2. Download the `google-services.json` file
3. Place it in: **`app/google-services.json`**

```
Bihar-Librarian-Quiz-apk/
├── app/
│   ├── google-services.json    ← Place file here
│   ├── build.gradle.kts
│   └── src/
└── ...
```

---

## Step 4: Enable Firebase Authentication

1. In Firebase Console, go to **Authentication**
2. Click **"Get started"**
3. Enable sign-in methods:

### 4a. Enable Google Sign-In
1. Click **"Google"** in the provider list
2. Toggle **"Enable"**
3. Set **Project public name:** Bihar Librarian Quiz
4. **Project support email:** (your email)
5. Click **"Save"**

### 4b. Enable Email/Password (Optional)
1. Click **"Email/Password"**
2. Toggle **"Enable"**
3. Save

---

## Step 5: Create Firestore Database

1. In Firebase Console, go to **Firestore Database**
2. Click **"Create database"**
3. Start in **Test mode** (for development)
   - Security rules allow read/write for testing
4. Select region: **us-central1** (or closest to you)
5. Click **"Create"**

### Security Rules (Production)
Once deployed, update security rules to protect user data:

```firestore
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Only authenticated users can access their own data
    match /users/{userId} {
      allow read, write: if request.auth.uid == userId;
      
      match /progress/{progressId} {
        allow read, write: if request.auth.uid == userId;
      }
      
      match /quiz_attempts/{attemptId} {
        allow read, write: if request.auth.uid == userId;
      }
    }
  }
}
```

---

## Step 6: Setup Firebase App Check (Optional but Recommended)

1. In Firebase Console, go to **App Check**
2. Click **"Add app"** → Select your Android app
3. Choose attestation provider: **Google Play Integrity**
4. The debug token will be shown in Android Studio logs

---

## Step 7: Sync Project in Android Studio

After placing `google-services.json`:

1. In Android Studio: **File** → **Sync Now**
2. Or run: `./gradlew build`
3. Gradle will auto-generate Firebase resources including `default_web_client_id`

---

## Step 8: Get Release SHA-1 (Before Publishing)

When ready to publish to Play Store:

```bash
./gradlew signingReport
```

Get the SHA-1 from **"release"** variant and add it to Firebase Console:
1. Go to **Project Settings** → **Your app** → **SHA certificate fingerprints**
2. Add the release SHA-1

---

## Verify Setup

Run the app:
```bash
./gradlew installDebug
# or in Android Studio: Run → Run 'app'
```

Test sign-in:
1. Navigate to **Settings** → **Sign In**
2. Try **Google Sign-In** → should open Google account selection
3. Or try **Student ID & Password** → local-only sign-in

---

## Environment Variables (.env)

You can also store Firebase config in `.env`:

```env
# .env (add to .gitignore)
FIREBASE_APPCHECK_DEBUG_TOKEN=your-debug-token-here
```

Refer to `.env.example` for the template.

---

## Troubleshooting

### "Google client ID not configured"
- Ensure `google-services.json` is in `app/` directory
- Run `./gradlew clean build` to regenerate resources
- Check that SHA-1 matches in Firebase Console

### Google Sign-In shows error
- Verify SHA-1 fingerprint is registered in Firebase
- Make sure Google sign-in is enabled in Firebase Auth
- Check internet connection

### Firestore rules deny read/write
- Start in **Test mode** during development
- Update security rules once you understand Firestore structure

---

## File Checklist

- [ ] `app/google-services.json` downloaded from Firebase
- [ ] `app/build.gradle.kts` has Firebase dependencies (already done)
- [ ] Firebase Authentication: Google Sign-In enabled
- [ ] Firestore Database created
- [ ] Debug SHA-1 added to Firebase Console
- [ ] Android app registered in Firebase with correct package name
- [ ] Project synced in Android Studio

---

## Next Steps

1. Complete all steps above
2. Test sign-in flow in the app
3. Verify cloud sync in Firestore
4. (Optional) Setup App Check for extra security
5. Before publishing: Add release SHA-1 to Firebase

---

For more details, see:
- [Firebase Setup Guide](https://firebase.google.com/docs/android/setup)
- [Firebase Authentication](https://firebase.google.com/docs/auth)
- [Firestore Database](https://firebase.google.com/docs/firestore)
