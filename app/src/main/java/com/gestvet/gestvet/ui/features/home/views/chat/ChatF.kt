package com.gestvet.gestvet.ui.features.home.views.chat

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.AlertDialog
import androidx.compose.ui.text.input.PasswordVisualTransformation

// Data class para los mensajes
data class Message(
    val text: String,
    val senderId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val id: String = ""
)

class ChatF : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContent {
            ForumChatApp()
        }
    }
}

@Composable
fun ForumChatApp() {
    val auth = FirebaseAuth.getInstance()
    val currentUser = remember { auth.currentUser }
    val navController = rememberNavController()

    val startDestination = if (currentUser != null) "chat" else "login"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable(
            route = "chat/{chatId}",
            arguments = listOf(navArgument("chatId") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            })
        ) { backStackEntry ->
            ChatScreen(
                navController = navController,
                chatId = backStackEntry.arguments?.getString("chatId")
            )
        }
        composable("chat") {
            ChatScreen(navController = navController, chatId = null)
        }
        composable("add_contact") {
            AddContactScreen(navController = navController)
        }
        composable("settings") {
            SettingsScreen(navController = navController)
        }
    }
}


@Composable
fun ChatScreen(
    navController: NavController,
    chatId: String? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat") },
                backgroundColor = Color(0xFF3F51B5),
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = { navController.navigate("add_contact") }) {
                        Icon(Icons.Default.Add, contentDescription = "Add Contact")
                    }
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (chatId == null) {
            StartChatScreen(navController)
        } else {
            ChatScreenContent(chatId = chatId)
        }
    }
}

@Composable
fun StartChatScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var contacts by remember { mutableStateOf(listOf<String>()) }
    val coroutineScope = rememberCoroutineScope()

    // Cargar contactos desde Firestore
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            currentUser?.let {
                val firestore = FirebaseFirestore.getInstance()
                val querySnapshot = firestore.collection("users")
                    .document(it.uid)
                    .collection("contacts")
                    .get()
                    .await()

                contacts = querySnapshot.documents.map { doc -> doc.getString("email") ?: "" }
            }
        }
    }

    StartSelectFriendScreen(
        contacts = contacts,
        onFriendSelected = { friendEmail ->
            val chatId = createChatId(currentUser?.email, friendEmail)
            val encodedChatId = URLEncoder.encode(chatId, StandardCharsets.UTF_8.toString())
            navController.navigate("chat/$encodedChatId")
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StartSelectFriendScreen(
    contacts: List<String>,
    onFriendSelected: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        if (contacts.isEmpty()) {
            Text(text = "No hay contactos disponibles. Agrega un nuevo contacto.")
        } else {
            LazyColumn {
                items(contacts.size) { index ->
                    val contact = contacts[index]
                    ListItem(
                        modifier = Modifier.clickable {
                            onFriendSelected(contact)
                        },
                        text = { Text(text = contact) },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) }
                    )
                }
            }
        }
    }
}

fun createChatId(currentUserEmail: String?, friendEmail: String): String {
    return if (currentUserEmail != null && friendEmail.isNotEmpty()) {
        val users = listOf(currentUserEmail, friendEmail).sorted()
        "${users[0]}_${users[1]}"
    } else {
        ""
    }
}

@Composable
fun ChatScreenContent(chatId: String) {
    var message by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()

    // Cargar mensajes desde Firestore y mantenerse escuchando cambios
    LaunchedEffect(chatId) {
        val chatRef = firestore.collection("chats").document(chatId)
            .collection("messages")
            .orderBy("timestamp")

        chatRef.addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e("Chat", "Error loading messages", error)
                return@addSnapshotListener
            }

            snapshot?.let { querySnapshot ->
                messages = querySnapshot.documents.mapNotNull { doc ->
                    Message(
                        text = doc.getString("text") ?: "",
                        senderId = doc.getString("senderId") ?: "",
                        timestamp = doc.getLong("timestamp") ?: 0L,
                        id = doc.id
                    )
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(
                    message = message,
                    isFromCurrentUser = message.senderId == currentUser?.uid
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Campo de entrada de mensaje
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            elevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = message,
                    onValueChange = { message = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    placeholder = { Text("Escribe un mensaje...") },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                IconButton(
                    onClick = {
                        if (message.isNotBlank() && currentUser != null) {
                            val newMessage = hashMapOf(
                                "text" to message.trim(),
                                "senderId" to currentUser.uid,
                                "timestamp" to System.currentTimeMillis()
                            )

                            coroutineScope.launch {
                                try {
                                    firestore.collection("chats")
                                        .document(chatId)
                                        .collection("messages")
                                        .add(newMessage)

                                    message = "" // Limpiar el campo después de enviar
                                } catch (e: Exception) {
                                    Log.e("Chat", "Error sending message", e)
                                }
                            }
                        }
                    },
                    enabled = message.isNotBlank()
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar",
                        tint = if (message.isBlank()) Color.Gray else Color(0xFF3F51B5)
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isFromCurrentUser: Boolean
) {
    val bubbleColor = if (isFromCurrentUser) {
        Color(0xFF3F51B5) // Color primario para mensajes propios
    } else {
        Color(0xFFE0E0E0) // Color gris claro para mensajes de otros
    }

    val textColor = if (isFromCurrentUser) {
        Color.White // Color blanco para el texto de mensajes propios
    } else {
        Color.Black // Color negro para el texto de mensajes de otros
    }

    Surface(
        shape = RoundedCornerShape(8.dp),
        color = bubbleColor,
        modifier = Modifier.fillMaxWidth(),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = message.text,
                color = textColor
            )
            Text(
                text = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(message.timestamp)),
                style = MaterialTheme.typography.body2,
                color = textColor.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
fun AddContactScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    val firestore = FirebaseFirestore.getInstance()
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Agregar Contacto", style = MaterialTheme.typography.h6)

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico del contacto") },
            modifier = Modifier.fillMaxWidth(),
            isError = errorMessage.isNotEmpty()
        )
        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (email.isNotBlank()) {
                coroutineScope.launch {
                    val existingContacts = firestore.collection("users")
                        .document(currentUser?.uid ?: "")
                        .collection("contacts")
                        .get()
                        .await()

                    val existingEmails = existingContacts.documents.map { it.getString("email") }

                    if (existingEmails.contains(email)) {
                        errorMessage = "El contacto ya existe."
                    } else {
                        firestore.collection("users")
                            .document(currentUser?.uid ?: "")
                            .collection("contacts")
                            .add(hashMapOf("email" to email.trim()))
                            .addOnSuccessListener {
                                navController.popBackStack()
                            }
                            .addOnFailureListener {
                                errorMessage = "Error al agregar contacto."
                            }
                    }
                }
            } else {
                errorMessage = "Por favor ingrese un correo electrónico."
            }
        }) {
            Text("Agregar")
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()
    val currentUser = auth.currentUser
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                backgroundColor = Color(0xFF3F51B5),
                contentColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Información del usuario
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Información de Usuario",
                        style = MaterialTheme.typography.h6,
                        color = Color(0xFF3F51B5)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Email: ${currentUser?.email ?: "No disponible"}",
                        style = MaterialTheme.typography.body1
                    )
                }
            }

            // Opciones de configuración
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Opciones",
                        style = MaterialTheme.typography.h6,
                        color = Color(0xFF3F51B5)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Cerrar Sesión")
                    }
                }
            }
        }

        if (showLogoutDialog) {
            AlertDialog(
                onDismissRequest = { showLogoutDialog = false },
                title = { Text("Cerrar Sesión") },
                text = { Text("¿Estás seguro que deseas cerrar sesión?") },
                confirmButton = {
                    Button(
                        onClick = {
                            auth.signOut()
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Red
                        )
                    ) {
                        Text("Sí, cerrar sesión", color = Color.White)
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { showLogoutDialog = false }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Forum Chat",
            style = MaterialTheme.typography.h4,
            color = Color(0xFF3F51B5),
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Button(
            onClick = {
                coroutineScope.launch {
                    try {
                        auth.signInWithEmailAndPassword(email, password).await()
                        navController.navigate("chat") {
                            popUpTo("login") { inclusive = true }
                        }
                    } catch (e: Exception) {
                        errorMessage = when {
                            e.message?.contains("password") == true -> "Contraseña incorrecta"
                            e.message?.contains("user") == true -> "Usuario no encontrado"
                            else -> "Error al iniciar sesión"
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = email.isNotEmpty() && password.isNotEmpty()
        ) {
            Text("Iniciar Sesión")
        }

        TextButton(
            onClick = {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    coroutineScope.launch {
                        try {
                            auth.createUserWithEmailAndPassword(email, password).await()
                            // Crear documento de usuario en Firestore
                            val firestore = FirebaseFirestore.getInstance()
                            val userId = auth.currentUser?.uid
                            userId?.let {
                                firestore.collection("users")
                                    .document(it)
                                    .set(hashMapOf(
                                        "email" to email,
                                        "createdAt" to System.currentTimeMillis()
                                    ))
                            }
                            navController.navigate("chat") {
                                popUpTo("login") { inclusive = true }
                            }
                        } catch (e: Exception) {
                            errorMessage = when {
                                e.message?.contains("email") == true -> "Email ya registrado"
                                e.message?.contains("password") == true -> "Contraseña débil"
                                else -> "Error al registrar usuario"
                            }
                        }
                    }
                } else {
                    errorMessage = "Por favor complete todos los campos"
                }
            }
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}