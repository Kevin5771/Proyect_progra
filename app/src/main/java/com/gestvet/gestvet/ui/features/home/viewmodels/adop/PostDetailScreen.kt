package com.gestvet.gestvet.ui.features.home.viewmodels.adop

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gestvet.gestvet.ui.theme.GestVetTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PostDetailScreen(viewModel: PostViewModel = viewModel()) {
    val post = viewModel.selectedPost ?: return
    GestVetTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            post.nombre,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }, // Estilo del título
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.secondary, // Color de la AppBar
                        titleContentColor = MaterialTheme.colorScheme.onSecondary // Color del título
                    )
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp) // Espaciado alrededor de los elementos
                    .fillMaxSize()
            ) {
                Text(
                    text = "Raza: ${post.raza}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Estilo para Raza
                    modifier = Modifier.padding(vertical = 8.dp) // Espaciado vertical
                )
                Text(
                    text = "Características: ${post.caracteristicas}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Estilo para Características
                    modifier = Modifier.padding(vertical = 8.dp) // Espaciado vertical
                )
                Text(
                    text = "Contacto: ${post.contacto}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp), // Estilo para Contacto
                    modifier = Modifier.padding(vertical = 8.dp) // Espaciado vertical
                )
                post.imagen?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = "Imagen de ${post.nombre}",
                        modifier = Modifier
                            .size(200.dp)
                            .padding(top = 16.dp) // Espaciado superior
                    )
                }
            }
        }
    }
}
