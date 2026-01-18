package com.example.movementatlas.domain.entity

/**
 * Represents a grouping of 1-3 Steps that form a compositional unit.
 * StepUnits handle state transitions and are used to build Sequences.
 */
data class StepUnit(
    val id: String,
    val name: String,
    val tags: List<String>,
    val steps: List<Step>,
    val preconditions: List<State>,
    val postState: State,
    val type: StepType
) {
    init {
        require(steps.isNotEmpty() && steps.size <= 3) {
            "StepUnit must contain 1-3 steps, but found ${steps.size}"
        }
        require(steps.all { it.type == type }) {
            "All steps in StepUnit must have the same type as the StepUnit"
        }
        // Validate that steps form a valid sequence
        validateStepSequence()
    }

    private fun validateStepSequence() {
        if (steps.isEmpty()) return
        
        // For solo steps, validate weight transfers chain correctly
        if (type == StepType.SOLO) {
            for (i in 0 until steps.size - 1) {
                val current = steps[i]
                val next = steps[i + 1]
                require(current.weightFootTo == next.weightFootFrom) {
                    "Steps must chain correctly: step $i ends on ${current.weightFootTo}, but step ${i + 1} starts on ${next.weightFootFrom}"
                }
            }
        }
        // For partner steps, validate both lead and follow chain correctly
        else if (type == StepType.PARTNER) {
            for (i in 0 until steps.size - 1) {
                val current = steps[i]
                val next = steps[i + 1]
                require(current.leadTo == next.leadFrom) {
                    "Lead steps must chain correctly: step $i ends on ${current.leadTo}, but step ${i + 1} starts on ${next.leadFrom}"
                }
                require(current.followTo == next.followFrom) {
                    "Follow steps must chain correctly: step $i ends on ${current.followTo}, but step ${i + 1} starts on ${next.followFrom}"
                }
            }
        }
    }

    /**
     * Computes the postState from the steps in this StepUnit.
     * Assumes the steps are applied sequentially from the initial state.
     */
    fun computePostState(initialState: State): State {
        var currentState = initialState
        steps.forEach { step ->
            currentState = applyStepToState(currentState, step)
        }
        return currentState
    }

    private fun applyStepToState(state: State, step: Step): State {
        return when {
            state is State.Solo && step.type == StepType.SOLO -> {
                State.Solo(SoloState(step.weightFootTo!!))
            }
            state is State.Partner && step.type == StepType.PARTNER -> {
                State.Partner(
                    PartnerState(
                        lead = SoloState(step.leadTo!!),
                        follow = SoloState(step.followTo!!)
                    )
                )
            }
            else -> throw IllegalArgumentException("Cannot apply step ${step.id} to state $state")
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is StepUnit) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
