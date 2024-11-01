package com.gestvet.gestvet.ui.features.service

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gestvet.gestvet.ui.features.home.viewmodels.service.ServiceCard
import com.gestvet.gestvet.ui.theme.GestVetTheme

class ServiceListActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestVetTheme {
                ServiceListScreen(onBackClick = { finish() })
            }
        }
    }
}

@Composable
fun ServiceListScreen(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background) // Fondo consistente con el tema
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Servicios de Cuidado para Mascotas",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        ServiceCard(
            name = "Juan Carlos Manuel Lechuga Ortíz",
            description = "Especialista en cuidado y entrenamiento básico para perros.\n" +
                    "\n Contacto: +502 556782949",
            price = 250.0
        )

        ServiceCard(
            name = "Melvin Daniel Sandoval",
            description = "Experto en socialización y cuidado de razas grandes.\n" +
                    "\n Contacto: +502 5962",
            price = 300.0
        )

        ServiceCard(
            name = "Kevin Josué Gutiérrez Linares",
            description = "Ofrece servicio de paseos diarios y monitoreo de salud.\n" +
                    "\n Contacto: +502 55212866",
            price = 200.0
        )
        ServiceCard(
            name = "Cristopher Antonio Berganza",
            description = "Ofrece servicio de paseos diarios y monitoreo de salud, además experto en socialización y cuidado de razas grandes.\n" +
                    "\n Contacto: +502 31434501 ",
            price = 175.0
        )

        Spacer(modifier = Modifier.height(20.dp))


        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        ) {
            Text(text = "Volver")
        }
    }

        Spacer(modifier = Modifier.height(10.dp))


}
@Composable
fun ServiceSection(name: String, description: String, price: Double) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = description,
                style = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Precio: Q$price",
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}
