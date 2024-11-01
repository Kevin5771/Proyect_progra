package com.gestvet.gestvet.ui.features.home.viewmodels.adop


import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(navController: NavHostController, viewModel: PostViewModel = viewModel()) {
    val posts = viewModel.posts

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Publicaciones") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->  // Usar el padding proporcionado por Scaffold
        Column(
            modifier = Modifier
                .padding(paddingValues)  // Asegura que no haya superposición con la AppBar
                .fillMaxSize()
                .padding(16.dp) // Padding adicional para los márgenes
        ) {
            if (posts.isEmpty()) {
                Text(
                    "No hay publicaciones disponibles.",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                posts.forEach { post ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)  // Espaciado entre las tarjetas
                            .clickable {
                                viewModel.selectPost(post)
                                navController.navigate("postDetail") // Navegar a la pantalla de detalles
                            },
                        elevation = CardDefaults.cardElevation(4.dp) // Agregar algo de elevación para la tarjeta
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)  // Espaciado interno dentro de la tarjeta
                        ) {
                            Text(text = "Nombre: ${post.nombre}", style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))  // Espacio entre los textos
                            Text(text = "Raza: ${post.raza}", style = MaterialTheme.typography.bodyMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = "Características: ${post.caracteristicas}", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }
    }
}
