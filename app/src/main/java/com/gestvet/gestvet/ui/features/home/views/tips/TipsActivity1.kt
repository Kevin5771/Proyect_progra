package com.gestvet.gestvet.ui.features.home.views.tips

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gestvet.gestvet.ui.theme.GestVetTheme

class TipsActivity1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestVetTheme {
                TipsScreen(
                    onBackClick = { finish() },
                    onVisitClick = { openUrl("https://madridsalud.es/cuidados-basicos-para-los-animales-de-compania-perros-y-gatos/") }
                )
            }
        }
    }

    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            println("No hay ninguna aplicación que pueda manejar este enlace.")
        }
    }
}

@Composable
fun TipsScreen(onBackClick: () -> Unit, onVisitClick: () -> Unit) {
    GestVetTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background) // Fondo consistente
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Consejos para el Cuidado de Mascotas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground, // Consistencia con el tema
                modifier = Modifier.padding(bottom = 20.dp)
            )

            TipSection(
                title = "Cómo Bañarlas:",
                content = "1. Usa agua tibia y un champú adecuado para su tipo de piel.\n" +
                        "2. Evita el uso de productos humanos, ya que alteran el pH de su piel.\n" +
                        "3. Inspecciona regularmente sus orejas para evitar infecciones.\n" +
                        "4. Recorta las uñas después del baño, ya que estarán más blandas.\n" +
                        "5. Si tu mascota tiene miedo al agua, usa juguetes y refuerzos positivos.\n\n" +
                        "Justificación: Un baño adecuado mantiene la higiene y evita problemas de piel. Evitar productos incorrectos previene irritaciones y alergias.",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer

            )

            TipSection(
                title = "Frecuencia de Visitas al Veterinario:",
                content = "1. Programa visitas anuales para chequeos generales y vacunación.\n" +
                        "2. Realiza controles semestrales en mascotas mayores de 7 años.\n" +
                        "3. Detecta temprano problemas dentales con revisiones regulares.\n" +
                        "4. Mantén desparasitaciones internas y externas al día.\n" +
                        "5. Lleva un registro médico para controlar la salud a largo plazo.\n\n" +
                        "Justificación: Las visitas periódicas permiten la detección precoz de enfermedades y mantienen la salud preventiva.",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

            TipSection(
                title = "Alimentación Balanceada:",
                content = "1. Asegúrate de proporcionar dietas específicas según la etapa de vida.\n" +
                        "2. Evita sobras de la mesa, especialmente alimentos tóxicos como el chocolate.\n" +
                        "3. Utiliza suplementos recomendados por veterinarios si es necesario.\n" +
                        "4. Controla las raciones para prevenir obesidad y problemas digestivos.\n" +
                        "5. Cambia de alimento gradualmente para evitar molestias estomacales.\n\n" +
                        "Justificación: Una dieta adecuada es clave para la energía, crecimiento y longevidad de las mascotas.",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

            TipSection(
                title = "Cómo Ser Cariñoso con Ellos:",
                content = "1. Usa el lenguaje corporal para comunicarte de forma positiva.\n" +
                        "2. Crea rutinas de juegos que fortalezcan su vínculo.\n" +
                        "3. Refuerza el buen comportamiento con premios.\n" +
                        "4. Evita los castigos físicos, ya que generan miedo y ansiedad.\n" +
                        "5. Proporciónale un lugar propio para que pueda descansar sin molestias.\n\n" +
                        "Justificación: El bienestar emocional es esencial para su comportamiento y calidad de vida.",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

            TipSection(
                title = "Otros Consejos:",
                content = "1. Identificación: Usa collares con placas o microchips para evitar extravíos.\n" +
                        "2. Ejercicio Diario: Las mascotas necesitan actividad física para mantenerse saludables.\n" +
                        "3. Socialización: Introduce a tu mascota a otros animales desde temprana edad.\n" +
                        "4. Enriquecimiento Ambiental: Ofrece juguetes y estímulos mentales para evitar aburrimiento.\n" +
                        "5. Control de Parásitos: Aplica tratamientos regulares para pulgas, garrapatas y otros parásitos.\n\n" +
                        "Justificación: Estos aspectos complementan los cuidados esenciales y mejoran su bienestar general.",
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
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

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun TipSection(title: String, content: String, backgroundColor: Color) {
    val textColor = contentColorFor(backgroundColor) // Ajuste dinámico del color del texto

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            BasicText(
                text = content,
                style = LocalTextStyle.current.copy(
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = textColor
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
