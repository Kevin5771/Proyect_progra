package com.gestvet.gestvet.ui.features.home.viewmodels.Forum


import androidx.lifecycle.ViewModel
import com.gestvet.gestvet.ui.features.home.models.PostForum
import com.gestvet.gestvet.ui.features.home.models.Review
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError

class PostViewModelF : ViewModel() {
    private val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("posts")

    // Método para crear una nueva publicación
    fun createPost(title: String, content: String) {
        val postId = database.push().key ?: return // Genera un ID único
        val newPost = PostForum(
            id = postId,
            title = title,
            content = content,
            likes = 0 // Inicializa los likes en 0
        )
        database.child(postId).setValue(newPost) // Agrega la publicación a Firebase
    }

    // Método para agregar una nueva publicación con un objeto Post existente
    fun addPost(post: PostForum) {
        val postId = database.push().key ?: return // Genera un ID único
        database.child(postId).setValue(post.copy(id = postId)) // Agrega la publicación a Firebase con el ID
    }

    // Método para obtener las publicaciones en tiempo real
    fun getPosts(callback: (List<PostForum>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fetchedPosts = snapshot.children.mapNotNull { it.getValue(PostForum::class.java) }
                callback(fetchedPosts) // Devuelve la lista actualizada
            }

            override fun onCancelled(error: DatabaseError) {
                callback(emptyList()) // En caso de error, retorna una lista vacía
            }
        })
    }

    // Método para agregar una revisión
    fun addReview(postId: String, comment: String, rating: Int) {
        val reviewId = database.child(postId).child("reviews").push().key ?: return
        val review = Review(comment = comment, rating = rating)
        database.child(postId).child("reviews").child(reviewId).setValue(review)
    }

    // Método para actualizar los likes de una publicación
    fun updatePost(postId: String, increment: Boolean) {
        database.child(postId).get().addOnSuccessListener { snapshot ->
            val post = snapshot.getValue(PostForum::class.java)
            post?.let {
                val updatedLikes = if (increment) {
                    it.likes + 1 // Incrementa los likes
                } else {
                    it.likes - 1 // Decrementa los likes
                }
                // Actualiza el post en Firebase
                database.child(postId).setValue(it.copy(likes = updatedLikes))
            }
        }
    }
}
