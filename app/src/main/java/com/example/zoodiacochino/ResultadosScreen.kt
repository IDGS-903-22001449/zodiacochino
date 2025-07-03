package com.example.zoodiacochino

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ResultadosScreen(
    viewModel: ZodiacoViewModel,
    onNavigateToStart: () -> Unit
) {
    val persona = viewModel.persona
    val edad = viewModel.calcularEdad()
    val signo = viewModel.leerSignoGuardado() ?: "Desconocido"
    val calificacion = viewModel.calificacion

    val iconosSignos = mapOf(
        "Rata" to "🐭",
        "Buey" to "🐂",
        "Tigre" to "🐅",
        "Conejo" to "🐰",
        "Dragón" to "🐉",
        "Serpiente" to "🐍",
        "Caballo" to "🐎",
        "Cabra" to "🐐",
        "Mono" to "🐵",
        "Gallo" to "🐓",
        "Perro" to "🐕",
        "Cerdo" to "🐷"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Hola ${persona.nombre} ${persona.apePaterno}, ${persona.apeMaterno}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = "Tienes $edad años y tu signo zodiacal",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(16.dp))

                iconosSignos[signo]?.let { icono ->
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .background(
                                Color(0xFFF5F5F5),
                                CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = icono,
                            fontSize = 60.sp
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Es $signo",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = "Calificación $calificacion",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        calificacion >= 8 -> Color(0xFF4CAF50)
                        calificacion >= 6 -> Color(0xFFFF9800)
                        else -> Color(0xFFF44336)
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Button(
            onClick = {
                viewModel.limpiarFormulario()
                onNavigateToStart()
            }
        ) {
            Text("Nuevo Examen")
        }
    }
}
