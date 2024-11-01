package com.example.chatapp2

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val auth = remember { FirebaseAuth.getInstance() }
    val firestore = remember { FirebaseFirestore.getInstance() }

    // Estado para el correo y la contraseña
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Para ocultar la contraseña
        )

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Button(
            onClick = {
                if (email.isNotBlank() && password.isNotBlank()) {
                    isLoggingIn = true
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            isLoggingIn = false
                            if (task.isSuccessful) {
                                Log.d("LoginScreen", "Inicio de sesión exitoso: ${auth.currentUser?.email}")
                                Toast.makeText(context, "Bienvenido ${auth.currentUser?.email}", Toast.LENGTH_SHORT).show()

                                // Guardar el usuario en Firestore
                                saveUserToFirestore(auth.currentUser?.uid, email)

                                onLoginSuccess()
                            } else {
                                Log.e("LoginScreen", "Error de autenticación: ${task.exception?.localizedMessage}")
                                errorMessage = task.exception?.localizedMessage ?: "Error al iniciar sesión"
                            }
                        }
                } else {
                    errorMessage = "Por favor, completa todos los campos."
                }
            },
            enabled = !isLoggingIn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoggingIn) "Iniciando sesión..." else "Iniciar sesión")
        }
    }
}

// Función para guardar el usuario en Firestore
private fun saveUserToFirestore(userId: String?, email: String) {
    if (userId != null) {
        val firestore = FirebaseFirestore.getInstance()
        val userData = hashMapOf(
            "email" to email
            // Puedes agregar otros campos si es necesario
        )

        firestore.collection("users").document(userId).set(userData)
            .addOnSuccessListener {
                Log.d("LoginScreen", "Usuario guardado en Firestore: $email")
            }
            .addOnFailureListener { e ->
                Log.e("LoginScreen", "Error al guardar usuario en Firestore: ${e.message}")
            }
    } else {
        Log.e("LoginScreen", "Error: el ID de usuario es nulo.")
    }
}
