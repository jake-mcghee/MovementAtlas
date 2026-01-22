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
     * Optional human-readable title for this step unit.
     */
    abstract val title: String?
    
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

    /**
     * DistanceOne: Single step with optional rotation.
     */
    data class DistanceOne(
        val step: Step,
        override val rotation: Rotation? = null,
        override val title: String? = null
    ) : StepUnit() {
        override val steps: List<Step> = listOf(step)
    }
    
    /**
     * DistanceTwo: A pattern where the first foot doesn't travel again.
     * 
     * Pattern:
     * 1. step1: First foot steps in a direction
     * 2. step2: Second foot steps in a direction (nullable - can be null if there's no explicit second step)
     * 3. step3: Weight transfers back to first foot (first foot stays in place, doesn't travel)
     * 
     * Step directions are relative to the dancer's orientation at that moment.
     * 
     * **Key Distinction from DistanceThree**: In DistanceTwo, the first foot doesn't travel again
     * (weight just returns to it in place). In DistanceThree, the first foot travels twice.
     */
    data class DistanceTwo(
        val step1: Step,
        val step2: Step? = null,
        val step3: Step,
        val dominantStartingFoot: WeightFoot? = null,
        val rotationGoesInTheDirectionOfTheFirstSteppingFoot: Boolean = true,
        override val rotation: Rotation? = null,
        override val title: String? = null
    ) : StepUnit() {
        private val controlStep = step2 ?: Step.InPlace
        override val steps: List<Step> = listOfNotNull(step1, controlStep, step3)
    }
    
    /**
     * DistanceThree: A pattern where the first foot travels twice.
     * 
     * Pattern:
     * 1. step1: First foot steps in a direction
     * 2. step2: Control step (weight transfer, typically IN_PLACE) - nullable
     * 3. step3: First foot steps again in a direction (first foot travels twice)
     * 
     * 
     * **Key Distinction from DistanceTwo**: In DistanceThree, the first foot travels twice
     * (step1 and step3 are both on the first foot). In DistanceTwo, the first foot doesn't travel again
     * (weight just returns to it in place via step3).
     */
    data class DistanceThree(
        val step1: Step,
        val step2: Step? = Step.InPlace,
        val step3: Step,
        val dominantStartingFoot: WeightFoot? = null,
        val rotationGoesInTheDirectionOfTheFirstSteppingFoot: Boolean = true,
        override val rotation: Rotation? = null,
        override val title: String? = null
    ) : StepUnit() {
        private val controlStep = step2 ?: Step.InPlace

        override val steps: List<Step> = listOf(step1, controlStep, step3)
    }
}
