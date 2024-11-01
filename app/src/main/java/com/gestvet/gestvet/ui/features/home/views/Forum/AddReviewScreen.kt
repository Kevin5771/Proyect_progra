package com.gestvet.gestvet.ui.features.home.views.Forum

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gestvet.gestvet.ui.features.home.viewmodels.Forum.PostViewModelF

@Composable
fun AddReviewScreen(postId: String, viewModel: PostViewModelF) {
    var comment by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        contentAlignment = Alignment.Center // Centra el contenido en la pantalla
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Agregar Reseña",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentario") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = if (rating > 0) rating.toString() else "",
                onValueChange = {
                    rating = it.toIntOrNull()?.coerceIn(1, 5) ?: 0
                },
                label = { Text("Valoración (1-5)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (comment.isNotEmpty() && rating in 1..5) {
                        viewModel.addReview(postId, comment, rating)
                        comment = ""
                        rating = 0
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Text("Enviar Reseña")
            }
        }
    }
}
