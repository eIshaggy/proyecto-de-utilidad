package com.up.calculadoradeviaje

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.up.calculadoradeviaje.ui.PantallaCalculadoraViaje
import com.up.calculadoradeviaje.ui.theme.CalculadoraDeViajeTheme
import com.up.calculadoradeviaje.viewmodel.CalculoViajeViewModel
import com.up.calculadoradeviaje.viewmodel.FormularioViajeViewModel

class MainActivity : ComponentActivity() {
    private val formularioViewModel: FormularioViajeViewModel by viewModels()
    private val calculoViewModel: CalculoViajeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraDeViajeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaCalculadoraViaje(
                        modifier = Modifier.padding(innerPadding),
                        formularioViewModel = formularioViewModel,
                        calculoViewModel = calculoViewModel
                    )
                }
            }
        }
    }
}
