package com.gestvet.gestvet.ui.features.home.views.adop

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gestvet.gestvet.ui.features.home.views.chat.LoginChat
import com.gestvet.gestvet.ui.features.home.views.tips.TipsActivity1
import com.gestvet.gestvet.ui.features.service.ServiceHome
import com.gestvet.gestvet.ui.theme.GestVetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdopScreen() {
    val context = LocalContext.current
    val activityContext = context as? ComponentActivity

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Adopciones",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary, // Cambiado
                    titleContentColor = MaterialTheme.colorScheme.onSecondary // Cambiado
                )
            )
        },
        content = { paddingValues ->
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                color = MaterialTheme.colorScheme.background // Fondo consistente
            ) {
                Box(
                    modifier = Modifier.padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        ActionButton(
                            text = "Abrir Adopciones",
                            icon = Icons.Default.Pets,
                            onClick = {
                                activityContext?.let {
                                    val intent = Intent(it, Adop::class.java)
                                    it.startActivity(intent)
                                } ?: println("Error: Contexto no es una instancia de ComponentActivity")
                            }
                        )

                        ActionButton(
                            text = "Ir al Chat",
                            icon = Icons.Default.Chat,
                            onClick = {
                                activityContext?.let {
                                    val intent = Intent(it, LoginChat::class.java)
                                    it.startActivity(intent)
                                } ?: println("Error: Contexto no es una instancia de ComponentActivity")
                            }
                        )

                        ActionButton(
                            text = "Ver Consejos",
                            icon = Icons.Default.Lightbulb,
                            onClick = {
                                activityContext?.let {
                                    val intent = Intent(it, TipsActivity1::class.java)
                                    it.startActivity(intent)
                                } ?: println("Error: Contexto no es una instancia de ComponentActivity")
                            }
                        )
                        ActionButton(
                            text = "Ver Consejos",
                            icon = Icons.Default.Lightbulb,
                            onClick = {
                                activityContext?.let {
                                    val intent = Intent(it, ServiceHome::class.java)
                                    it.startActivity(intent)
                                } ?: println("Error: Contexto no es una instancia de ComponentActivity")
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ActionButton(
    text: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer, // Consistente
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer // Consistente
        )
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AdopScreenPreview() {
    GestVetTheme {
        AdopScreen()
    }
}
