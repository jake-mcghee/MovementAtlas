package com.example.movementatlas.presentation.model

/**
 * UI model for displaying a sequence of movement step units.
 * Decoupled from domain entity for presentation-layer flexibility.
 */
data class SequenceUiModel(
    val stepUnits: List<StepUnitUiModel>,
    val startStateDisplay: String,
    val endStateDisplay: String,
    val stepUnitCount: Int
)
