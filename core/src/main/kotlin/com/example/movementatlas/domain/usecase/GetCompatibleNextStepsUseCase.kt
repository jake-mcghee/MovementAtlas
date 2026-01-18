package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StateTransitionRules
import kotlinx.coroutines.flow.first

class GetCompatibleNextStepsUseCase(
    private val stepRepository: StepRepository,
    private val transitionRules: StateTransitionRules
) {
    suspend operator fun invoke(fromState: State): List<com.example.movementatlas.domain.entity.Step> {
        val allSteps = stepRepository.getAllSteps().first()
        return allSteps.filter { step ->
            transitionRules.isValidTransition(fromState, step)
        }
    }
}
