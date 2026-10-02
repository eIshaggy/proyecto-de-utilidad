package com.up.calculadoradeviaje.viewmodel

import androidx.lifecycle.ViewModel
import com.up.calculadoradeviaje.R
import com.up.calculadoradeviaje.model.ExpenseLevel
import com.up.calculadoradeviaje.model.FuelTripUiState
import com.up.calculadoradeviaje.model.FuelType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.NumberFormat
import java.util.Locale

class   FuelTripViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<FuelTripUiState>(FuelTripUiState.Initial)
    val uiState: StateFlow<FuelTripUiState> = _uiState.asStateFlow()

    private val _distance = MutableStateFlow("")
    val distance: StateFlow<String> = _distance.asStateFlow()

    private val _efficiency = MutableStateFlow(15f)
    val efficiency: StateFlow<Float> = _efficiency.asStateFlow()

    private val _fuelType = MutableStateFlow(FuelType.REGULAR)
    val fuelType: StateFlow<FuelType> = _fuelType.asStateFlow()

    private val _includeTolls = MutableStateFlow(false)
    val includeTolls: StateFlow<Boolean> = _includeTolls.asStateFlow()

    fun updateDistance(newDistance: String) {
        // Validación: Solo permitir números y un punto decimal
        if (newDistance.isEmpty() || newDistance.matches(Regex("^\\d*\\.?\\d*$"))) {
            _distance.value = newDistance
            calculateTrip()
        }
    }

    fun updateEfficiency(newEfficiency: Float) {
        _efficiency.value = newEfficiency
        calculateTrip()
    }

    fun updateFuelType(newType: FuelType) {
        _fuelType.value = newType
        calculateTrip()
    }

    fun updateIncludeTolls(include: Boolean) {
        _includeTolls.value = include
        calculateTrip()
    }

    private fun calculateTrip() {
        val distanceValue = _distance.value.toDoubleOrNull()
        if (distanceValue == null || distanceValue <= 0.0) {
            _uiState.value = FuelTripUiState.Initial
            return
        }

        val liters = distanceValue / _efficiency.value
        var cost = liters * _fuelType.value.pricePerLiter
        if (_includeTolls.value) {
            cost += 250.0
        }

        val (expenseLevel, progress, textRes) = when {
            cost <= 800 -> Triple(ExpenseLevel.LOW, 0.3f, R.string.expense_low)
            cost <= 2000 -> Triple(ExpenseLevel.MEDIUM, 0.65f, R.string.expense_medium)
            else -> Triple(ExpenseLevel.HIGH, 1.0f, R.string.expense_high)
        }

        val numberFormat = NumberFormat.getCurrencyInstance(Locale("es", "MX"))
        val formattedCost = numberFormat.format(cost)
        val formattedLiters = String.format(Locale.getDefault(), "%.2f", liters)

        _uiState.value = FuelTripUiState.Success(
            litersNeeded = formattedLiters,
            totalCost = formattedCost,
            expenseLevelTextRes = textRes,
            expenseLevel = expenseLevel,
            progress = progress
        )
    }
}
