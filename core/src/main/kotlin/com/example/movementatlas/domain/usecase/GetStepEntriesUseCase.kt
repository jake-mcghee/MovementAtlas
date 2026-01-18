package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.entity.State

class GetStepEntriesUseCase {
    operator fun invoke(step: Step): List<State> {
        return step.preconditions.filter { state ->
            state.canTransitionTo(step)
        }
    }
}
