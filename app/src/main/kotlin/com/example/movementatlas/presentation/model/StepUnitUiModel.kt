package com.example.movementatlas.presentation.model

/**
 * UI model for displaying a StepUnit (grouping of 1-3 steps).
 * Decoupled from domain entity for presentation-layer flexibility.
 */
data class StepUnitUiModel(
    val id: String,
    val name: String,
    val tags: List<String>,
    val difficulty: String,
    val stepCount: Int // Number of steps in this unit (1-3)
)
