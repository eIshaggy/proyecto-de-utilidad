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
import com.up.calculadoradeviaje.model.EstadoUiViaje
import com.up.calculadoradeviaje.model.TipoCombustible
import com.up.calculadoradeviaje.ui.components.ControlDeslizanteRendimiento
import com.up.calculadoradeviaje.ui.components.TarjetaResumen
import com.up.calculadoradeviaje.viewmodel.CalculoViajeViewModel
import com.up.calculadoradeviaje.viewmodel.FormularioViajeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaCalculadoraViaje(
    modifier: Modifier = Modifier,
    formularioViewModel: FormularioViajeViewModel,
    calculoViewModel: CalculoViajeViewModel
) {
    val estadoUi by calculoViewModel.estadoUi.collectAsStateWithLifecycle()
    val distancia by formularioViewModel.distancia.collectAsStateWithLifecycle()
    val rendimiento by formularioViewModel.rendimiento.collectAsStateWithLifecycle()
    val tipoCombustible by formularioViewModel.tipoCombustible.collectAsStateWithLifecycle()
    val incluirCasetas by formularioViewModel.incluirCasetas.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        OutlinedTextField(
            value = distancia,
            onValueChange = { nuevaDistancia ->
                if (formularioViewModel.actualizarDistancia(nuevaDistancia)) {
                    calculoViewModel.ejecutarCalculo(
                        distanciaTexto = nuevaDistancia,
                        rendimiento = rendimiento,
                        tipoCombustible = tipoCombustible,
                        incluirCasetas = incluirCasetas
                    )
                }
            },
            label = { Text(stringResource(R.string.etiqueta_distancia)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        ControlDeslizanteRendimiento(
            rendimiento = rendimiento,
            alCambiarRendimiento = { nuevoRendimiento ->
                formularioViewModel.actualizarRendimiento(nuevoRendimiento)
                calculoViewModel.ejecutarCalculo(
                    distanciaTexto = distancia,
                    rendimiento = nuevoRendimiento,
                    tipoCombustible = tipoCombustible,
                    incluirCasetas = incluirCasetas
                )
            }
        )

        Column {
            Text(
                text = stringResource(R.string.etiqueta_tipo_combustible),
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TipoCombustible.entries.forEach { tipo ->
                    val idResTexto = when(tipo) {
                        TipoCombustible.REGULAR -> R.string.combustible_regular
                        TipoCombustible.PREMIUM -> R.string.combustible_premium
                        TipoCombustible.DIESEL -> R.string.combustible_diesel
                    }
                    FilterChip(
                        selected = tipoCombustible == tipo,
                        onClick = { 
                            formularioViewModel.actualizarTipoCombustible(tipo)
                            calculoViewModel.ejecutarCalculo(
                                distanciaTexto = distancia,
                                rendimiento = rendimiento,
                                tipoCombustible = tipo,
                                incluirCasetas = incluirCasetas
                            )
                        },
                        label = { Text(stringResource(idResTexto)) }
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
                text = stringResource(R.string.incluir_casetas),
                style = MaterialTheme.typography.bodyLarge
            )
            Switch(
                checked = incluirCasetas,
                onCheckedChange = { incluir ->
                    formularioViewModel.actualizarIncluirCasetas(incluir)
                    calculoViewModel.ejecutarCalculo(
                        distanciaTexto = distancia,
                        rendimiento = rendimiento,
                        tipoCombustible = tipoCombustible,
                        incluirCasetas = incluir
                    )
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        when (val estado = estadoUi) {
            is EstadoUiViaje.Inicial -> {
                Text(
                    text = stringResource(R.string.mensaje_inicial),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            is EstadoUiViaje.Exitoso -> {
                TarjetaResumen(estadoExitoso = estado)
            }
        }
    }
}
