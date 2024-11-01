package com.example.chatapp2

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun SelectFriendScreen(onFriendSelected: (String) -> Unit) {
    val db = FirebaseFirestore.getInstance()
    val users = remember { mutableListOf<String>() } // Lista de correos de usuarios

    // Obtener la lista de usuarios de Firestore
    LaunchedEffect(Unit) {
        db.collection("users").get().addOnSuccessListener { snapshot ->
            for (doc in snapshot.documents) {
                val email = doc.getString("email")
                if (email != null) {
                    users.add(email)
                }
            }
        }
    }

    LazyColumn {
        items(users) { userEmail ->
            Button(onClick = { onFriendSelected(userEmail) }) {
                Text(userEmail)
            }
        }
    }
}
