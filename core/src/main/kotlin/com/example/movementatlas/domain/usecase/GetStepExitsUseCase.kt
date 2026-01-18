package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.State

class GetStepUnitExitsUseCase {
    operator fun invoke(stepUnit: StepUnit): List<State> {
        return stepUnit.preconditions
            .filter { state -> state.canTransitionTo(stepUnit) }
            .map { state -> state.applyTransition(stepUnit) }
    }
}
