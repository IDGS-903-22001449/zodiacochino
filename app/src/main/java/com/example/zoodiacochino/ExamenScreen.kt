package com.example.zoodiacochino

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExamenScreen(viewModel: ZodiacoViewModel, onFinishExam: () -> Unit) {
    val preguntasExamen = viewModel.preguntasExamen
    val respuestasUsuario = viewModel.respuestasUsuario
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Examen de Zodiaco Chino",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Text(
            text = "Responde todas las preguntas",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            preguntasExamen.forEachIndexed { preguntaIndex, pregunta ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = "${preguntaIndex + 1}. ${pregunta.enunciado}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )

                        pregunta.opciones.forEachIndexed { opcionIndex, opcion ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp)
                            ) {
                                RadioButton(
                                    selected = respuestasUsuario.getOrNull(preguntaIndex) == opcionIndex,
                                    onClick = {
                                        val nuevasRespuestas = respuestasUsuario.toMutableList()
                                        nuevasRespuestas[preguntaIndex] = opcionIndex
                                        viewModel.actualizarRespuestas(nuevasRespuestas)
                                    }
                                )
                                Text(
                                    text = "${('a' + opcionIndex).uppercase()}) $opcion",
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        Button(
            onClick = {
                viewModel.terminarExamen()
                onFinishExam()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            enabled = respuestasUsuario.all { it != -1 }
        ) {
            Text("Terminar Examen")
        }
    }
}
