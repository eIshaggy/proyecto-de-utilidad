package com.up.calculadoradeviaje.model

enum class TipoCombustible(val precioPorLitro: Double) {
    REGULAR(23.77),
    PREMIUM(28.61),
    DIESEL(27.01)
}

enum class NivelGasto(val colorArgb: Long) {
    BAJO(0xFF4CAF50),
    MEDIO(0xFFFF9800),
    ALTO(0xFFF44336)
}

sealed interface EstadoUiViaje {
    data object Inicial : EstadoUiViaje
    
    data class Exitoso(
        val litrosNecesarios: String,
        val costoTotal: String,
        val idTextoNivelGasto: Int,
        val nivelGasto: NivelGasto,
        val progreso: Float
    ) : EstadoUiViaje
}
