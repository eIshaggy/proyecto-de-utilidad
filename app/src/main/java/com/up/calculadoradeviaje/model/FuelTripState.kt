package com.up.calculadoradeviaje.model

enum class FuelType(val pricePerLiter: Double) {
    REGULAR(23.77),
    PREMIUM(28.61),
    DIESEL(27.01)
}

enum class ExpenseLevel(val colorArgb: Long) {
    LOW(0xFF4CAF50),
    MEDIUM(0xFFFF9800),
    HIGH(0xFFF44336)
}

sealed interface FuelTripUiState {
    data object Initial : FuelTripUiState
    
    data class Success(
        val litersNeeded: String,
        val totalCost: String,
        val expenseLevelTextRes: Int,
        val expenseLevel: ExpenseLevel,
        val progress: Float
    ) : FuelTripUiState
}
