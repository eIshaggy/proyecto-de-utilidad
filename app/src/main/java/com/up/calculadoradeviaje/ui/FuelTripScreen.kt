    package com.up.calculadoradeviaje.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.up.calculadoradeviaje.R
import com.up.calculadoradeviaje.model.FuelTripUiState
import com.up.calculadoradeviaje.model.FuelType
import com.up.calculadoradeviaje.ui.components.EfficiencySlider
import com.up.calculadoradeviaje.ui.components.SummaryCard
import com.up.calculadoradeviaje.viewmodel.FuelTripViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FuelTripScreen(
    modifier: Modifier = Modifier,
    viewModel: FuelTripViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val distance by viewModel.distance.collectAsStateWithLifecycle()
    val efficiency by viewModel.efficiency.collectAsStateWithLifecycle()
    val fuelType by viewModel.fuelType.collectAsStateWithLifecycle()
    val includeTolls by viewModel.includeTolls.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        OutlinedTextField(
            value = distance,
            onValueChange = viewModel::updateDistance,
            label = { Text(stringResource(R.string.distance_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        EfficiencySlider(
            efficiency = efficiency,
            onEfficiencyChange = viewModel::updateEfficiency
        )

        Column {
            Text(
                text = stringResource(R.string.fuel_type_label),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FuelType.entries.forEach { type ->
                    val labelRes = when(type) {
                        FuelType.REGULAR -> R.string.fuel_regular
                        FuelType.PREMIUM -> R.string.fuel_premium
                        FuelType.DIESEL -> R.string.fuel_diesel
                    }
                    FilterChip(
                        selected = fuelType == type,
                        onClick = { viewModel.updateFuelType(type) },
                        label = { Text(stringResource(labelRes)) }
                    )
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.include_tolls),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = includeTolls,
                onCheckedChange = viewModel::updateIncludeTolls
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (val state = uiState) {
            is FuelTripUiState.Initial -> {
                Text(
                    text = stringResource(R.string.initial_message),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is FuelTripUiState.Success -> {
                SummaryCard(successState = state)
            }
        }
    }
}
