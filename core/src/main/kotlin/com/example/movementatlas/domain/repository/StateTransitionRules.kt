package com.example.movementatlas.domain.repository

import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.entity.Step

interface StateTransitionRules {
    fun isValidTransition(from: State, step: Step): Boolean
    fun applyTransition(from: State, step: Step): State
}
