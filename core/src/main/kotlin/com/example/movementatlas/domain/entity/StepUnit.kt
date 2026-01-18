package com.example.movementatlas.domain.entity

/**
 * Represents a pattern for a grouping of 1-3 Steps that form a compositional unit.
 * Foot-agnostic pattern that can be applied starting from either foot.
 * StepUnits handle state transitions and are used to build Sequences.
 * Always per-person.
 * 
 * **Domain Invariant**: All StepUnits must end on the opposite foot from where they started.
 * This invariant is enforced by the computePostState method for all StepUnit types.
 */
sealed class StepUnit {
    abstract val rotation: Rotation?
    
    /**
     * Helper property to access step patterns as a list.
     */
    abstract val steps: List<Step>

    /**
     * Computes the ending weight foot after applying this StepUnit pattern
     * starting from the given initial weight foot.
     * 
     * **Domain Invariant**: All StepUnits must end on the opposite foot from where they started.
     */
    fun computePostState(initialWeightFoot: WeightFoot): WeightFoot {
        return when (initialWeightFoot) {
            WeightFoot.LEFT -> WeightFoot.RIGHT
            WeightFoot.RIGHT -> WeightFoot.LEFT
        }
    }

    data class DistanceOne(
        val step: Step,
        override val rotation: Rotation? = null
    ) : StepUnit() {
        override val steps: List<Step> = listOf(step)
    }
    
    data class DistanceTwo(
        val step1: Step,
        val step2: Step,
        override val rotation: Rotation? = null
    ) : StepUnit() {
        // Both step1 and step2 have meaningful directions
        // DistanceTwo semantic: After step2, weight returns to first foot which is IN_PLACE (doesn't travel).
        // This is the key differentiator from DistanceThree (where first foot travels twice).
        override val steps: List<Step> = listOf(step1, step2)
    }
    
    data class DistanceThree(
        val step1: Step,
        val step2: Step,
        val step3: Step,
        override val rotation: Rotation? = null
    ) : StepUnit() {
        // All three steps have meaningful directions
        override val steps: List<Step> = listOf(step1, step2, step3)
    }
}
