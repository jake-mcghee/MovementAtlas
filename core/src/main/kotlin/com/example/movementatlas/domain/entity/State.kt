package com.example.movementatlas.domain.entity

sealed class State {
    abstract fun canTransitionTo(step: Step): Boolean
    abstract fun applyTransition(step: Step): State
    
    data class Solo(val soloState: SoloState) : State() {
        override fun canTransitionTo(step: Step): Boolean {
            return step.preconditions.contains(this) && 
                   step.type == StepType.SOLO
        }
        
        override fun applyTransition(step: Step): State {
            require(canTransitionTo(step)) { 
                "Cannot transition from $this to step ${step.id}" 
            }
            return step.postState
        }
    }
    
    data class Partner(val partnerState: PartnerState) : State() {
        override fun canTransitionTo(step: Step): Boolean {
            return step.preconditions.contains(this) && 
                   step.type == StepType.PARTNER
        }
        
        override fun applyTransition(step: Step): State {
            require(canTransitionTo(step)) { 
                "Cannot transition from $this to step ${step.id}" 
            }
            return step.postState
        }
    }
}
