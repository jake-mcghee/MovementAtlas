package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of atomic movement steps (single weight transfers).
 * This is domain data that can be used by any platform implementation.
 */
object DefaultStepProvider {

    fun getDefaultSteps(): List<Step> = Direction.values().map { direction ->
        // Atomic step pattern - single weight transfer
        // This pattern can be applied starting from either foot
        Step(direction = direction)
    }
}
