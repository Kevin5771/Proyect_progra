package com.gestvet.gestvet.ui.features.home.views.chat

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatEntryScreen() {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") }
            )
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                val activityContext = context as? ComponentActivity
                Button(onClick = {
                    activityContext?.let {
                        val intent = Intent(it, LoginChat::class.java)
                        it.startActivity(intent)
                    } ?: run {
                        // Manejo de error
                        println("Error: Contexto no es una instancia de ComponentActivity")
                    }
                }) {
                    Text(text = "Abrir Chat")
                }
            }
        }
    )
}
