package com.example.movementatlas.domain.entity

/**
 * Represents a pattern for a grouping of 1-3 Steps that form a compositional unit.
 * Foot-agnostic pattern that can be applied starting from either foot.
 * StepUnits handle state transitions and are used to build Sequences.
 * Always per-person.
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
     * Steps are applied sequentially, each transferring to the opposite foot.
     */
    fun computePostState(initialWeightFoot: WeightFoot): WeightFoot {
        var currentFoot = initialWeightFoot
        steps.forEach { step ->
            currentFoot = step.endingFoot(currentFoot)
        }
        return currentFoot
    }

    /**
     * Checks if this StepUnit pattern can be applied from the given weight foot.
     * Since patterns are foot-agnostic, they can always be applied from any foot.
     */
    fun canTransitionFrom(weightFoot: WeightFoot): Boolean {
        return true // Patterns can be applied from any foot
    }
    
    /**
     * Gets the valid starting weight feet for this StepUnit pattern.
     * Since patterns are foot-agnostic, they can start from either foot.
     */
    fun preconditions(): List<WeightFoot> {
        return listOf(WeightFoot.LEFT, WeightFoot.RIGHT)
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
        // No validation needed - step patterns always chain correctly when applied sequentially
        // step1 transfers to opposite foot, step2 starts from that opposite foot
        override val steps: List<Step> = listOf(step1, step2)
    }
    
    data class DistanceThree(
        val step1: Step,
        val step2: Step,
        val step3: Step,
        override val rotation: Rotation? = null
    ) : StepUnit() {
        // No validation needed - step patterns always chain correctly when applied sequentially
        override val steps: List<Step> = listOf(step1, step2, step3)
    }
}
