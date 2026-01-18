package com.example.movementatlas.presentation.ui

import com.example.movementatlas.presentation.model.SequenceUiModel
import com.example.movementatlas.presentation.model.StartStateOption

/**
 * UI state for the Movement Atlas screen.
 * Uses presentation-layer models, decoupled from domain entities.
 */
data class SimpleUiState(
    val selectedStartState: StartStateOption? = null,
    val sequences: List<SequenceUiModel> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
