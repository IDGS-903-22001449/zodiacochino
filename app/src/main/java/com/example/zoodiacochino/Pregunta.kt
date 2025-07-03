package com.example.zoodiacochino

data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int
)

object BancoPreguntas {
    val preguntas = listOf(
        Pregunta(
            "¿Cuál es la suma de 2 + 2?",
            listOf("8", "6", "4", "3"),
            2
        ),
        Pregunta(
            "¿Cuál es la capital de México?",
            listOf("Berlín", "CDMX", "Guadalajara", "Tirana"),
            1
        ),
        Pregunta(
            "¿Cuál es el planeta más grande del sistema solar?",
            listOf("Sol", "Saturno", "Júpiter", "Tierra"),
            2
        ),
        Pregunta(
            "¿Cuál es el resultado de 7 - 3?",
            listOf("2", "4", "12", "15"),
            1
        ),
        Pregunta(
            "¿Qué dorsal lleva Cristiano Ronaldo?",
            listOf("1", "7", "25", "8"),
            1
        ),
        Pregunta(
            "¿Cuál es la capital de Albania?",
            listOf("Guadalajara", "Madrid", "Tirana", "3"),
            2
        ),
        Pregunta(
            "¿Cuál es la suma de 5 + 3?",
            listOf("7", "8", "9", "10"),
            1
        ),
        Pregunta(
            "¿Qué dorsal usa Messi en la Selección Argentina?",
            listOf("10", "9", "19", "30"),
            0
        ),
        Pregunta(
            "¿Cuál es el resultado de 10 - 4?",
            listOf("5", "6", "7", "8"),
            1
        ),
        Pregunta(
            "¿Cuántos lados tiene un triángulo equilátero?",
            listOf("2", "3", "18", "5"),
            1
        )
    )
}