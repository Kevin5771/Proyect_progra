package com.gestvet.gestvet.ui.features.home.viewmodels.adop

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gestvet.gestvet.ui.theme.GestVetTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CreatePostScreen(navController: NavController, postViewModel: PostViewModel) {
    var nombre by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var caracteristicas by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            uri?.let {
                val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                imageBitmap = bitmap
            }
        }
    }
    GestVetTheme{
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crear Publicación", color = MaterialTheme.colorScheme.onPrimary) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.secondaryContainer)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(65.dp)
                .fillMaxSize()
        ) {
            BasicTextField(
                value = nombre,
                onValueChange = { nombre = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground), // Color de texto
                decorationBox = { innerTextField ->
                    if (nombre.isEmpty()) Text("Nombre", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = raza,
                onValueChange = { raza = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                decorationBox = { innerTextField ->
                    if (raza.isEmpty()) Text("Raza", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = caracteristicas,
                onValueChange = { caracteristicas = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                decorationBox = { innerTextField ->
                    if (caracteristicas.isEmpty()) Text("Características", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            BasicTextField(
                value = contacto,
                onValueChange = { contacto = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                decorationBox = { innerTextField ->
                    if (contacto.isEmpty()) Text("Contacto", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    innerTextField()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    launcher.launch(intent)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Consistente
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Consistente
                )
            ) {
                Text("Seleccionar Imagen")
            }

            imageBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(vertical = 16.dp)
                )
            }

            Button(
                onClick = {
                    val post = Post(nombre, raza, caracteristicas, contacto, imageBitmap)
                    postViewModel.addPost(post)
                    navController.navigate("postList")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer, // Consistente
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Consistente
                )
            ) {
                Text("Publicar")
            }
        }
    }}
}
