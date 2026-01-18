package com.example.movementatlas.presentation.model

/**
 * UI model for displaying a sequence of movement steps.
 * Decoupled from domain entity for presentation-layer flexibility.
 */
data class SequenceUiModel(
    val steps: List<StepUiModel>,
    val startStateDisplay: String,
    val endStateDisplay: String,
    val stepCount: Int
)
