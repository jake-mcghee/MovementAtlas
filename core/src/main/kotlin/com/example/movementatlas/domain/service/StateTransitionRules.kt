package com.example.movementatlas.domain.service

import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.entity.StepType

/**
 * Contains the core domain logic for validating and applying state transitions.
 * This is pure business logic with no external dependencies.
 */
class StateTransitionRules {

    fun isValidTransition(from: State, step: Step): Boolean {
        // Check if the step's preconditions include the current state
        if (!step.preconditions.contains(from)) {
            return false
        }

        // For solo steps, ensure we're in a solo state
        if (step.type == StepType.SOLO && from !is State.Solo) {
            return false
        }

        // For partner steps, ensure we're in a partner state
        if (step.type == StepType.PARTNER && from !is State.Partner) {
            return false
        }

        return true
    }

    fun applyTransition(from: State, step: Step): State {
        // Return the step's postState
        // In a more complex implementation, we might compute this based on the current state
        return step.postState
    }
}
