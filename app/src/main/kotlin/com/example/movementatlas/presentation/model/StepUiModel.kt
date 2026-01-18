package com.example.movementatlas.presentation.model

/**
 * UI model for displaying a single movement step.
 * Decoupled from domain entity for presentation-layer flexibility.
 */
data class StepUiModel(
    val id: String,
    val name: String,
    val tags: List<String>,
    val difficulty: String
)
