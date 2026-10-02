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
import com.up.calculadoradeviaje.model.FuelTripUiState

@Composable
fun EfficiencySlider(
    efficiency: Float,
    onEfficiencyChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.efficiency_label, efficiency),
            style = MaterialTheme.typography.bodyLarge
        )
        Slider(
            value = efficiency,
            onValueChange = onEfficiencyChange,
            valueRange = 3f..80f,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun SummaryCard(
    successState: FuelTripUiState.Success,
    modifier: Modifier = Modifier
) {
    val dynamicColor = Color(successState.expenseLevel.colorArgb)

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
                text = stringResource(id = R.string.summary_title),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = stringResource(id = R.string.summary_liters, successState.litersNeeded),
                style = MaterialTheme.typography.bodyLarge
            )
            
            Text(
                text = stringResource(id = R.string.summary_cost, successState.totalCost),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = dynamicColor
            )
            
            Text(
                text = stringResource(id = successState.expenseLevelTextRes),
                style = MaterialTheme.typography.bodyMedium,
                color = dynamicColor
            )
            
            LinearProgressIndicator(
                progress = { successState.progress },
                modifier = Modifier.fillMaxWidth(),
                color = dynamicColor,
                trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
            )
        }
    }
}
