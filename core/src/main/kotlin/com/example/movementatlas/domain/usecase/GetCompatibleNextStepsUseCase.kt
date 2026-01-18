package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.repository.StepUnitRepository
import kotlinx.coroutines.flow.first

class GetCompatibleNextStepUnitsUseCase(
    private val stepUnitRepository: StepUnitRepository
) {
    suspend operator fun invoke(fromState: State): List<StepUnit> {
        val allStepUnits = stepUnitRepository.getAllStepUnits().first()
        return allStepUnits.filter { stepUnit ->
            fromState.canTransitionTo(stepUnit)
        }
    }
}
