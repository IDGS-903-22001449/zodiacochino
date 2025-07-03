package com.example.zoodiacochino

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class ZodiacoViewModel(private val context: Context) : ViewModel() {

    private val fileName = "signo.txt"
    private val resultadosFileName = "resultados_examen.txt"

    var persona by mutableStateOf(PersonaFormulario())
        private set

    var preguntasExamen by mutableStateOf<List<Pregunta>>(emptyList())
        private set
    var respuestasUsuario by mutableStateOf<List<Int>>(emptyList())
        private set
    var preguntaActual by mutableStateOf(0)
        private set
    var examenTerminado by mutableStateOf(false)
        private set
    var calificacion by mutableStateOf(0)
        private set
    
    data class PersonaFormulario(
        val nombre: String = "",
        val apePaterno: String = "",
        val apeMaterno: String = "",
        val dia: String = "",
        val mes: String = "",
        val anio: String = "",
        val sexo: String = ""
    )
    
    fun actualizarPersona(nuevaPersona: PersonaFormulario) {
        persona = nuevaPersona
    }
    
    fun calcularEdad(): Int {
        val anioNacimiento = persona.anio.toIntOrNull() ?: 2000
        return 2024 - anioNacimiento
    }

    fun calcularYGuardarSigno(anio: Int) {
        val signosChinos = listOf(
            "Mono", "Gallo", "Perro", "Cerdo", "Rata", "Buey",
            "Tigre", "Conejo", "Dragón", "Serpiente", "Caballo", "Cabra"
        )
        val signo = signosChinos[anio % 12]

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, fileName)
                file.writeText(signo)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun leerSignoGuardado(): String? {
        return try {
            val file = File(context.filesDir, fileName)
            if (file.exists()) file.readText() else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    fun iniciarExamen() {
        preguntasExamen = BancoPreguntas.preguntas
        respuestasUsuario = List(10) { -1 }
        preguntaActual = 0
        examenTerminado = false
        calificacion = 0
    }
    
    fun responderPregunta(respuesta: Int) {
        if (preguntaActual < respuestasUsuario.size) {
            respuestasUsuario = respuestasUsuario.toMutableList().apply {
                this[preguntaActual] = respuesta
            }
        }
    }
    
    fun actualizarRespuestas(nuevasRespuestas: List<Int>) {
        respuestasUsuario = nuevasRespuestas
    }

    fun terminarExamen() {
        var respuestasCorrectas = 0
        for (i in preguntasExamen.indices) {
            if (respuestasUsuario[i] == preguntasExamen[i].respuestaCorrecta) {
                respuestasCorrectas++
            }
        }
        calificacion = (respuestasCorrectas * 10) / preguntasExamen.size
        examenTerminado = true

        guardarResultados(respuestasCorrectas, preguntasExamen.size)
    }
    
    private fun guardarResultados(correctas: Int, total: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val file = File(context.filesDir, resultadosFileName)
                val resultado = "${persona.nombre} ${persona.apePaterno}: $correctas/$total - Calificación: $calificacion"
                file.writeText(resultado)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    fun limpiarFormulario() {
        persona = PersonaFormulario()
    }
}
