package com.example.movementatlas.domain.entity

sealed class State {
    abstract fun canTransitionTo(stepUnit: StepUnit): Boolean
    abstract fun applyTransition(stepUnit: StepUnit): State
    
    data class Solo(val soloState: SoloState) : State() {
        override fun canTransitionTo(stepUnit: StepUnit): Boolean {
            return stepUnit.preconditions.contains(this) && 
                   stepUnit.type == StepType.SOLO &&
                   stepUnit.steps.isNotEmpty() &&
                   stepUnit.steps.first().weightFootFrom == soloState.weightFoot
        }
        
        override fun applyTransition(stepUnit: StepUnit): State {
            require(canTransitionTo(stepUnit)) { 
                "Cannot transition from $this to stepUnit ${stepUnit.id}" 
            }
            return stepUnit.postState
        }
    }
    
    data class Partner(val partnerState: PartnerState) : State() {
        override fun canTransitionTo(stepUnit: StepUnit): Boolean {
            return stepUnit.preconditions.contains(this) && 
                   stepUnit.type == StepType.PARTNER &&
                   stepUnit.steps.isNotEmpty() &&
                   stepUnit.steps.first().leadFrom == partnerState.lead.weightFoot &&
                   stepUnit.steps.first().followFrom == partnerState.follow.weightFoot
        }
        
        override fun applyTransition(stepUnit: StepUnit): State {
            require(canTransitionTo(stepUnit)) { 
                "Cannot transition from $this to stepUnit ${stepUnit.id}" 
            }
            return stepUnit.postState
        }
    }
}
