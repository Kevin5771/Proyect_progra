package com.gestvet.gestvet.ui.features.home.viewmodels.adop

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

data class Post(
    val nombre: String,
    val raza: String,
    val caracteristicas: String,
    val contacto: String,
    val imagen: android.graphics.Bitmap?
)

class PostViewModel : ViewModel() {
    val posts = mutableStateListOf<Post>()
    var selectedPost: Post? = null

    fun addPost(post: Post) {
        posts.add(post)
    }

    fun selectPost(post: Post) {
        selectedPost = post
    }
}