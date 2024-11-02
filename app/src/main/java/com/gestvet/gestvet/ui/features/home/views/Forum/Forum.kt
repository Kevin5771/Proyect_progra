package com.gestvet.gestvet.ui.features.home.views.Forum

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.activity.ComponentActivity
import com.gestvet.gestvet.ui.features.home.viewmodels.Forum.PostViewModelF
import com.gestvet.gestvet.ui.theme.GestVetTheme

class Forum : ComponentActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
        GestVetTheme {
            Surface(color = MaterialTheme.colorScheme.background) {
                // Llama a la función de contenido principal
                AppContent(viewModel = PostViewModelF())
            }
        }
    }
}
}

@Composable
fun AppContent(viewModel: PostViewModelF) {
    PostListScreen(viewModel = viewModel)
}

@Composable
fun TestScreen() {
    Text(text = "¡Hola, mundo!", style = MaterialTheme.typography.titleLarge)
}
