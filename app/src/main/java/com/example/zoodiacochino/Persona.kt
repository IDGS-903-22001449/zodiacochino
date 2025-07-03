package com.example.zoodiacochino

import java.time.LocalDate

data class Persona(
     val nombre: String = "",
     val apePaterno: String = "",
     val apeMaterno: String = "",
     val fechaNacimiento: LocalDate,
     val sexo: String = ""
)