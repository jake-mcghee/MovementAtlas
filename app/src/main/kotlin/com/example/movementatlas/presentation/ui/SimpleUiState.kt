package com.example.movementatlas.presentation.ui

import com.example.movementatlas.domain.entity.Sequence
import com.example.movementatlas.domain.entity.SoloState

data class SimpleUiState(
    val selectedState: SoloState? = null,
    val sequences: List<Sequence> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
