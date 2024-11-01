package com.example.chatapp2

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.launch

data class UserProfile(
    var firstName: String = "",
    var lastName: String = "",
    var address: String = "",
    var lastUpdated: Long = System.currentTimeMillis()
)

sealed class ProfileUiState {
    object Loading : ProfileUiState()
    data class Success(val profile: UserProfile) : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}

@Composable
fun ProfileScreen(userEmail: String) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val db = remember { FirebaseFirestore.getInstance() }
    val snackbarHostState = remember { SnackbarHostState() }

    var uiState by remember { mutableStateOf<ProfileUiState>(ProfileUiState.Loading) }
    var isEditing by remember { mutableStateOf(false) }
    var isSaving by remember { mutableStateOf(false) }

    // Estado temporal para edición
    var editProfile by remember { mutableStateOf(UserProfile()) }

    // Cargar perfil
    LaunchedEffect(userEmail) {
        try {
            val document = db.collection("users").whereEqualTo("email", userEmail).get().await()
            val profile = document.documents.firstOrNull()?.toObject(UserProfile::class.java) ?: UserProfile()
            uiState = ProfileUiState.Success(profile)
            editProfile = profile
        } catch (e: Exception) {
            uiState = ProfileUiState.Error("Error al cargar el perfil: ${e.localizedMessage}")
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (uiState) {
                is ProfileUiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }

                is ProfileUiState.Success -> {
                    OutlinedTextField(
                        value = editProfile.firstName,
                        onValueChange = {
                            editProfile = editProfile.copy(firstName = it)
                            isEditing = true
                        },
                        label = { Text("Nombre") },
                        enabled = !isSaving,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editProfile.lastName,
                        onValueChange = {
                            editProfile = editProfile.copy(lastName = it)
                            isEditing = true
                        },
                        label = { Text("Apellido") },
                        enabled = !isSaving,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editProfile.address,
                        onValueChange = {
                            editProfile = editProfile.copy(address = it)
                            isEditing = true
                        },
                        label = { Text("Dirección") },
                        enabled = !isSaving,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = {
                                scope.launch {
                                    try {
                                        isSaving = true
                                        // Validar campos
                                        when {
                                            editProfile.firstName.isBlank() -> throw IllegalArgumentException("El nombre es requerido")
                                            editProfile.lastName.isBlank() -> throw IllegalArgumentException("El apellido es requerido")
                                            editProfile.address.isBlank() -> throw IllegalArgumentException("La dirección es requerida")
                                        }

                                        editProfile = editProfile.copy(lastUpdated = System.currentTimeMillis())
                                        db.collection("users")
                                            .document(userEmail) // Cambiado de userId a userEmail
                                            .set(editProfile, SetOptions.merge())
                                            .await()

                                        uiState = ProfileUiState.Success(editProfile)
                                        isEditing = false
                                        snackbarHostState.showSnackbar("Perfil actualizado correctamente")
                                    } catch (e: Exception) {
                                        snackbarHostState.showSnackbar(
                                            message = e.localizedMessage ?: "Error al guardar el perfil"
                                        )
                                    } finally {
                                        isSaving = false
                                    }
                                }
                            },
                            enabled = isEditing && !isSaving,
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (isSaving) "Guardando..." else "Guardar")
                        }

                        if (isEditing) {
                            OutlinedButton(
                                onClick = {
                                    editProfile = (uiState as ProfileUiState.Success).profile
                                    isEditing = false
                                },
                                enabled = !isSaving,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("Cancelar")
                            }
                        }
                    }
                }

                is ProfileUiState.Error -> {
                    Text(
                        text = (uiState as ProfileUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(
                        onClick = {
                            uiState = ProfileUiState.Loading
                            scope.launch {
                                try {
                                    val document = db.collection("users")
                                        .whereEqualTo("email", userEmail) // Cambiado de userId a userEmail
                                        .get()
                                        .await()
                                    val profile = document.documents.firstOrNull()?.toObject(UserProfile::class.java) ?: UserProfile()
                                    uiState = ProfileUiState.Success(profile)
                                    editProfile = profile
                                } catch (e: Exception) {
                                    uiState = ProfileUiState.Error(
                                        "Error al recargar el perfil: ${e.localizedMessage}"
                                    )
                                }
                            }
                        }
                    ) {
                        Text("Reintentar")
                    }
                }
            }
        }
    }
}
