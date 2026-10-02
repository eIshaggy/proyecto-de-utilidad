package com.up.calculadoradeviaje.viewmodel

import androidx.lifecycle.ViewModel
import com.up.calculadoradeviaje.model.TipoCombustible
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FormularioViajeViewModel : ViewModel() {

    private val _distancia = MutableStateFlow("")
    val distancia: StateFlow<String> = _distancia.asStateFlow()

    private val _rendimiento = MutableStateFlow(15f)
    val rendimiento: StateFlow<Float> = _rendimiento.asStateFlow()

    private val _tipoCombustible = MutableStateFlow(TipoCombustible.REGULAR)
    val tipoCombustible: StateFlow<TipoCombustible> = _tipoCombustible.asStateFlow()

    private val _incluirCasetas = MutableStateFlow(false)
    val incluirCasetas: StateFlow<Boolean> = _incluirCasetas.asStateFlow()

    fun actualizarDistancia(nueva: String): Boolean {
        if (nueva.isEmpty() || nueva.matches(Regex("^\\d*\\.?\\d*$"))) {
            _distancia.value = nueva
            return true
        }
        return false
    }

    fun actualizarRendimiento(nuevo: Float) {
        _rendimiento.value = nuevo
    }

    fun actualizarTipoCombustible(nuevo: TipoCombustible) {
        _tipoCombustible.value = nuevo
    }

    fun actualizarIncluirCasetas(incluir: Boolean) {
        _incluirCasetas.value = incluir
    }
}
