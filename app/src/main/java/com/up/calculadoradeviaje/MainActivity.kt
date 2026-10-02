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
import com.up.calculadoradeviaje.ui.FuelTripScreen
import com.up.calculadoradeviaje.ui.theme.CalculadoraDeViajeTheme
import com.up.calculadoradeviaje.viewmodel.FuelTripViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: FuelTripViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraDeViajeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FuelTripScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
