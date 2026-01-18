package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.repository.StepRepository
import kotlinx.coroutines.flow.first

class GetCompatibleNextStepsUseCase(
    private val stepRepository: StepRepository
) {
    suspend operator fun invoke(fromState: State): List<com.example.movementatlas.domain.entity.Step> {
        val allSteps = stepRepository.getAllSteps().first()
        return allSteps.filter { step ->
            fromState.canTransitionTo(step)
        }
    }
}
