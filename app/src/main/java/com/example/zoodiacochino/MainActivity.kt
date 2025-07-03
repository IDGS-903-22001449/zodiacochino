package com.example.zoodiacochino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext
import com.example.zoodiacochino.ui.theme.ZoodiacochinoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ZoodiacochinoTheme {
                ZodiacoApp()
            }
        }
    }
}

@Composable
fun ZodiacoApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    
    val viewModel: ZodiacoViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ZodiacoViewModel::class.java)) {
                    return ZodiacoViewModel(context) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )
    
    NavHost(
        navController = navController,
        startDestination = "formulario"
    ) {
        composable("formulario") {
            FormularioScreen(
                onNavigateToExamen = { 
                    viewModel.iniciarExamen()
                    navController.navigate("examen")
                },
                viewModel = viewModel
            )
        }
        
        composable("examen") {
            ExamenScreen(
                viewModel = viewModel,
                onFinishExam = {
                    navController.navigate("resultados")
                }
            )
        }
        
        composable("resultados") {
            ResultadosScreen(
                viewModel = viewModel,
                onNavigateToStart = {
                    navController.navigate("formulario") {
                        popUpTo("formulario") { inclusive = true }
                    }
                }
            )
        }
    }
}
