package com.example.movementatlas.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movementatlas.domain.entity.WeightFoot
import com.example.movementatlas.domain.usecase.GenerateSequencesUseCase
import com.example.movementatlas.domain.usecase.GetCompatibleNextStepUnitsUseCase
import com.example.movementatlas.presentation.mapper.UiModelMapper.toWeightFoot
import com.example.movementatlas.presentation.mapper.UiModelMapper.toUiModels
import com.example.movementatlas.presentation.model.StartStateOption
import com.example.movementatlas.presentation.ui.SimpleUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovementAtlasViewModel @Inject constructor(
    private val generateSequencesUseCase: GenerateSequencesUseCase,
    private val getCompatibleNextStepUnitsUseCase: GetCompatibleNextStepUnitsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SimpleUiState())
    val uiState: StateFlow<SimpleUiState> = _uiState.asStateFlow()

    fun generateSequences(startStateOption: StartStateOption) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null, selectedStartState = startStateOption) }
            try {
                val startWeightFoot = startStateOption.toWeightFoot()
                generateSequencesUseCase(startWeightFoot, 5)
                    .collect { sequences ->
                        _uiState.update {
                            it.copy(
                                sequences = sequences.toUiModels(),
                                isLoading = false,
                                error = null
                            )
                        }
                    }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error occurred"
                    )
                }
            }
        }
    }
}
