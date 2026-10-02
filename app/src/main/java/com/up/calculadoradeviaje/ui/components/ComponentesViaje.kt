package com.up.calculadoradeviaje.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.up.calculadoradeviaje.R
import com.up.calculadoradeviaje.model.EstadoUiViaje

@Composable
fun ControlDeslizanteRendimiento(
    rendimiento: Float,
    alCambiarRendimiento: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.etiqueta_rendimiento, rendimiento),
            style = MaterialTheme.typography.bodyLarge
        )
        Slider(
            value = rendimiento,
            onValueChange = alCambiarRendimiento,
            valueRange = 3f..80f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun TarjetaResumen(
    estadoExitoso: EstadoUiViaje.Exitoso,
    modifier: Modifier = Modifier
) {
    val colorDinamico = Color(estadoExitoso.nivelGasto.colorArgb)

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = stringResource(id = R.string.titulo_resumen),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = stringResource(id = R.string.resumen_litros, estadoExitoso.litrosNecesarios),
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = stringResource(id = R.string.resumen_costo, estadoExitoso.costoTotal),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = colorDinamico
            )
            
            Text(
                text = stringResource(id = estadoExitoso.idTextoNivelGasto),
                style = MaterialTheme.typography.bodyMedium,
                color = colorDinamico
            )
            
            LinearProgressIndicator(
                progress = { estadoExitoso.progreso },
                modifier = Modifier.fillMaxWidth(),
                color = colorDinamico,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}
