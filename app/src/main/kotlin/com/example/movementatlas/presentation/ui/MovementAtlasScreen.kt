package com.example.movementatlas.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movementatlas.presentation.model.StartStateOption
import com.example.movementatlas.presentation.viewmodel.MovementAtlasViewModel

@Composable
fun MovementAtlasScreen(
    viewModel: MovementAtlasViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Movement Atlas",
            style = MaterialTheme.typography.headlineMedium
        )

        // Start state selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Start State:")
            Button(
                onClick = { viewModel.generateSequences(StartStateOption.LEFT) },
                enabled = !uiState.isLoading
            ) {
                Text(StartStateOption.LEFT.displayName)
            }
            Button(
                onClick = { viewModel.generateSequences(StartStateOption.RIGHT) },
                enabled = !uiState.isLoading
            ) {
                Text(StartStateOption.RIGHT.displayName)
            }
        }

        // Loading indicator
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }

        // Error message
        uiState.error?.let { error ->
            Text(
                text = "Error: $error",
                color = MaterialTheme.colorScheme.error
            )
        }

        // Generated sequences
        if (uiState.sequences.isNotEmpty()) {
            Text(
                text = "Generated Sequences (${uiState.sequences.size}):",
                style = MaterialTheme.typography.titleMedium
            )

            uiState.sequences.forEachIndexed { index, sequence ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = "Sequence ${index + 1}",
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            text = "Steps: ${sequence.steps.joinToString(", ") { it.name }}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        } else if (!uiState.isLoading && uiState.error == null && uiState.selectedStartState != null) {
            Text("No sequences found")
        }
    }
}
