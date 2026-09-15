package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.AppScreen
import com.example.ui.QuizViewModel
import com.example.ui.screens.AddQuestionScreen
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MistakesScreen
import com.example.ui.screens.QuestionBankScreen
import com.example.ui.screens.QuizPlayScreen
import com.example.ui.screens.QuizResultScreen
import com.example.ui.theme.BiharLibrarianTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BiharLibrarianTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: QuizViewModel = viewModel()
                    MainAppContent(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: QuizViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsState()

    AnimatedContent(
        targetState = currentScreen,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = "ScreenTransition"
    ) { screen ->
        when (screen) {
            is AppScreen.Home -> HomeScreen(viewModel = viewModel)
            is AppScreen.CategoryList -> HomeScreen(viewModel = viewModel)
            is AppScreen.QuizPlay -> QuizPlayScreen(viewModel = viewModel)
            is AppScreen.QuizResult -> QuizResultScreen(viewModel = viewModel)
            is AppScreen.AddQuestion -> AddQuestionScreen(viewModel = viewModel)
            is AppScreen.QuestionBank -> QuestionBankScreen(viewModel = viewModel)
            is AppScreen.Bookmarks -> BookmarksScreen(viewModel = viewModel)
            is AppScreen.Mistakes -> MistakesScreen(viewModel = viewModel)
            is AppScreen.StudyMode -> QuestionBankScreen(
                viewModel = viewModel,
                initialCategoryFilter = screen.category
            )
        }
    }
}
