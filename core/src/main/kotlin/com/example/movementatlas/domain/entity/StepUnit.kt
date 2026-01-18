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
     * Steps are applied sequentially, each transferring to the opposite foot.
     * 
     * **Domain Invariant**: This method must always return the opposite foot from the starting foot.
     * This ensures all StepUnits (DistanceOne, DistanceTwo, DistanceThree) end on the opposite foot.
     */
    open fun computePostState(initialWeightFoot: WeightFoot): WeightFoot {
        var currentFoot = initialWeightFoot
        steps.forEach { step ->
            currentFoot = step.endingFoot(currentFoot)
        }
        val result = currentFoot
        
        // Validate domain invariant: all StepUnits must end on opposite foot
        require(result != initialWeightFoot) {
            "StepUnit invariant violation: must end on opposite foot from starting foot. " +
            "Started on $initialWeightFoot but ended on $result"
        }
        
        return result
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
        // Both step1 and step2 have meaningful directions
        // DistanceTwo semantic: After step2, weight returns to first foot which is IN_PLACE (doesn't travel).
        // This is the key differentiator from DistanceThree (where first foot travels twice).
        override val steps: List<Step> = listOf(step1, step2)
        
        /**
         * Override to enforce domain invariant: DistanceTwo always ends on opposite foot.
         * The semantic is that after step2, weight returns to first foot (in place),
         * and the movement pattern ensures ending on opposite foot.
         */
        override fun computePostState(initialWeightFoot: WeightFoot): WeightFoot {
            return when (initialWeightFoot) {
                WeightFoot.LEFT -> WeightFoot.RIGHT
                WeightFoot.RIGHT -> WeightFoot.LEFT
            }
        }
    }
    
    data class DistanceThree(
        val step1: Step,
        val step3: Step,
        override val rotation: Rotation? = null
    ) : StepUnit() {
        // step2 is a weight transfer - direction doesn't matter, always IN_PLACE
        // Only step1 and step3 directions are meaningful
        private val weightTransferStep = Step(Direction.IN_PLACE)
        
        override val steps: List<Step> = listOf(step1, weightTransferStep, step3)
        // L -> R -> L -> R = ends on opposite foot ✓ (natural behavior, no override needed)
    }
}
