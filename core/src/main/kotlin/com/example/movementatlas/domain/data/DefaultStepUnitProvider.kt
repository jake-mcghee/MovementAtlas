package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of StepUnits (groupings of 1-3 steps).
 * This is domain data that can be used by any platform implementation.
 */
object DefaultStepUnitProvider {

    fun getDefaultStepUnits(steps: List<Step>): List<StepUnit> {
        // Get the step pattern (foot-agnostic)
        val stepPattern = steps.firstOrNull { it.direction == Direction.IN_PLACE }
            ?: throw IllegalStateException("Step pattern not found")

        return listOf(
            // Single step pattern - can be applied from either foot
            StepUnit.DistanceOne(step = stepPattern),
            // Double step pattern - both step1 and step2 directions matter
            // After step2, weight returns to first foot (in place), then transfers to opposite foot
            StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern),
            // Triple step pattern - only step1 and step3 directions matter
            // step2 is a weight transfer (direction doesn't matter, computed internally)
            StepUnit.DistanceThree(step1 = stepPattern, step3 = stepPattern)
        )
    }
}
