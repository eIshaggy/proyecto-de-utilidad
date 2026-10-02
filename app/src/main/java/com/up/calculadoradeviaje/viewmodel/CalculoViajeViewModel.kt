package com.up.calculadoradeviaje.viewmodel

import androidx.lifecycle.ViewModel
import com.up.calculadoradeviaje.R
import com.up.calculadoradeviaje.model.EstadoUiViaje
import com.up.calculadoradeviaje.model.NivelGasto
import com.up.calculadoradeviaje.model.TipoCombustible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import java.util.Locale

class CalculoViajeViewModel : ViewModel() {

    private val _estadoUi = MutableStateFlow<EstadoUiViaje>(EstadoUiViaje.Inicial)
    val estadoUi: StateFlow<EstadoUiViaje> = _estadoUi.asStateFlow()

    fun ejecutarCalculo(
        distanciaTexto: String,
        rendimiento: Float,
        tipoCombustible: TipoCombustible,
        incluirCasetas: Boolean
    ) {
        val valorDistancia = distanciaTexto.toDoubleOrNull()
        if (valorDistancia == null || valorDistancia <= 0.0) {
            _estadoUi.value = EstadoUiViaje.Inicial
            return
        }

        val litros = valorDistancia / rendimiento
        var costo = litros * tipoCombustible.precioPorLitro
        if (incluirCasetas) {
            costo += 250.0 // Monto fijo de casetas
        }

        val (nivelGasto, progreso, idResTexto) = when {
            costo <= 800 -> Triple(NivelGasto.BAJO, 0.3f, R.string.gasto_bajo)
            costo <= 2000 -> Triple(NivelGasto.MEDIO, 0.65f, R.string.gasto_medio)
            else -> Triple(NivelGasto.ALTO, 1.0f, R.string.gasto_alto)
        }

        val formatoNumero = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        val costoFormateado = formatoNumero.format(costo)
        val litrosFormateados = String.format(Locale.getDefault(), "%.2f", litros)

        _estadoUi.value = EstadoUiViaje.Exitoso(
            litrosNecesarios = litrosFormateados,
            costoTotal = costoFormateado,
            idTextoNivelGasto = idResTexto,
            nivelGasto = nivelGasto,
            progreso = progreso
        )
    }
}
