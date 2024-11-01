package com.gestvet.gestvet.ui.features.home.views.adop



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gestvet.gestvet.ui.features.home.viewmodels.adop.CreatePostScreen
import com.gestvet.gestvet.ui.features.home.viewmodels.adop.PostDetailScreen
import com.gestvet.gestvet.ui.features.home.viewmodels.adop.PostListScreen
import com.gestvet.gestvet.ui.features.home.viewmodels.adop.PostViewModel

class Adop : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            // Crear una única instancia de PostViewModel
            val postViewModel: PostViewModel = viewModel()

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    MenuScreen(navController)
                }
                composable("createPost") {
                    CreatePostScreen(navController, postViewModel)
                }
                composable("postList") {
                    PostListScreen(navController, postViewModel)
                }
                composable("postDetail") {
                    PostDetailScreen(postViewModel)
                }
            }
        }
    }
}
