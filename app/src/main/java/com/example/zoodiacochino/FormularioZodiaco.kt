package com.example.zoodiacochino

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun FormularioScreen(
    onNavigateToExamen: () -> Unit,
    viewModel: ZodiacoViewModel
) {
    val persona = viewModel.persona

    var nombre by remember { mutableStateOf(persona.nombre) }
    var apePaterno by remember { mutableStateOf(persona.apePaterno) }
    var apeMaterno by remember { mutableStateOf(persona.apeMaterno) }
    var dia by remember { mutableStateOf(persona.dia) }
    var mes by remember { mutableStateOf(persona.mes) }
    var anio by remember { mutableStateOf(persona.anio) }
    var sexo by remember { mutableStateOf(persona.sexo) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Datos Personales",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 26.dp, bottom = 24.dp)
        )

        val textFieldModifier = Modifier
            .weight(1f)
            .padding(vertical = 4.dp)

        val textFieldStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp)

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Nombre: ", modifier = Modifier.padding(end = 8.dp))
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                textStyle = textFieldStyle,
                modifier = textFieldModifier
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Apellido paterno: ", modifier = Modifier.padding(end = 8.dp))
            OutlinedTextField(
                value = apePaterno,
                onValueChange = { apePaterno = it },
                label = { Text("Apellido paterno") },
                textStyle = textFieldStyle,
                modifier = textFieldModifier
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Apellido materno: ", modifier = Modifier.padding(end = 8.dp))
            OutlinedTextField(
                value = apeMaterno,
                onValueChange = { apeMaterno = it },
                label = { Text("Apellido materno") },
                textStyle = textFieldStyle,
                modifier = textFieldModifier
            )
        }

        Text(
            text = "Fecha de nacimiento",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 24.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            listOf(
                Triple("Día", dia, { it: String -> dia = it }),
                Triple("Mes", mes, { it: String -> mes = it }),
                Triple("Año", anio, { it: String -> anio = it })
            ).forEach { (label, value, onChange) ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = label, textAlign = TextAlign.Center)
                    OutlinedTextField(
                        value = value,
                        onValueChange = { if (it.all { c -> c.isDigit() }) onChange(it) },
                        singleLine = true,
                        modifier = Modifier.width(80.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        textStyle = textFieldStyle
                    )
                }
            }
        }

        Text(
            text = "Sexo",
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(top = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = sexo == "Masculino",
                onClick = { sexo = "Masculino" }
            )
            Text(text = "Masculino")

            Spacer(modifier = Modifier.width(16.dp))

            RadioButton(
                selected = sexo == "Femenino",
                onClick = { sexo = "Femenino" }
            )
            Text(text = "Femenino")
        }

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
        ) {
            Button(onClick = {
                nombre = ""
                apePaterno = ""
                apeMaterno = ""
                dia = ""
                mes = ""
                anio = ""
                sexo = ""
                viewModel.limpiarFormulario()
            }) {
                Text("Limpiar")
            }

            Button(onClick = {
                viewModel.actualizarPersona(
                    ZodiacoViewModel.PersonaFormulario(
                        nombre = nombre,
                        apePaterno = apePaterno,
                        apeMaterno = apeMaterno,
                        dia = dia,
                        mes = mes,
                        anio = anio,
                        sexo = sexo
                    )
                )
                anio.toIntOrNull()?.let {
                    viewModel.calcularYGuardarSigno(it)
                    onNavigateToExamen()
                }
            }) {
                Text("Siguiente")
            }
        }
    }
}
