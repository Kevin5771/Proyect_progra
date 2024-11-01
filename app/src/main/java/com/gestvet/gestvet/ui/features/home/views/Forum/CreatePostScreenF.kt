package com.gestvet.gestvet.ui.features.home.views.Forum

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gestvet.gestvet.ui.features.home.viewmodels.Forum.PostViewModelF
import com.gestvet.gestvet.ui.theme.GestVetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(viewModel: PostViewModelF) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    GestVetTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Crear Publicación", color = MaterialTheme.colorScheme.onPrimary) },
                    navigationIcon = {
                        IconButton(onClick = { /* Acción de navegación */ }) {
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
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Crear Publicación",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                BasicTextField(
                    value = title,
                    onValueChange = { title = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    decorationBox = { innerTextField ->
                        if (title.isEmpty()) Text("Título", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                BasicTextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()) Text("Contenido", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                        innerTextField()
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (title.isNotEmpty() && content.isNotEmpty()) {
                            viewModel.createPost(title, content)
                        } else {
                            Log.e("CreatePostScreen", "Título o contenido vacío")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text("Crear Publicación", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
