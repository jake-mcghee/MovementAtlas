package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.service.StateTransitionRules

class GetStepEntriesUseCase(
    private val transitionRules: StateTransitionRules
) {
    operator fun invoke(step: Step): List<State> {
        return step.preconditions.filter { state ->
            transitionRules.isValidTransition(state, step)
        }
    }
}
