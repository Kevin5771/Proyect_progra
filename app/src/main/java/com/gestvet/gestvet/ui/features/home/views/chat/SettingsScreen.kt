package com.example.chatapp2

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsScreen(onLogout: () -> Unit) {
    Button(onClick = {
        FirebaseAuth.getInstance().signOut()
        onLogout()
    }) {
        Text("Logout")
    }
}
