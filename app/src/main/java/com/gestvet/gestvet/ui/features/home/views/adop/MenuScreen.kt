package com.gestvet.gestvet.ui.features.home.views.adop

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.gestvet.gestvet.ui.theme.GestVetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {
    GestVetTheme{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Adopciones App", style = MaterialTheme.typography.titleLarge, color = Color.White) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary, // Color de la AppBar
                    titleContentColor = MaterialTheme.colorScheme.secondary// Color del título
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Fondo consistente
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp), // Espaciado vertical
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActionButton(
                    text = "Crear Publicación",
                    onClick = { navController.navigate("createPost") }
                )

                ActionButton(
                    text = "Ver Publicaciones",
                    onClick = { navController.navigate("postList") }
                )
            }
        }
    }
}}

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Color del botón
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Color del texto del botón
        )
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp) // Estilo del texto
        )
    }
}
