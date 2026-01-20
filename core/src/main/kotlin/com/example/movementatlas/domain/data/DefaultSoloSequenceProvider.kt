package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of common solo sequences (hardcoded curated sequences).
 * These are domain data that can be used by any platform implementation.
 * 
 * Common sequences are separate from user-saved sequences (which are stored via SequenceRepository).
 * Common sequences have null IDs since they are not persisted.
 * 
 * To add a new common sequence:
 * 1. Create a function (e.g., `myNewSequence()`) using the `commonSequence` helper
 * 2. Add it to the [allSequences] list
 */
object DefaultSoloSequenceProvider {



    /**
     * Returns a list of common solo sequences.
     * Each sequence represents a well-known movement pattern that can be reused.
     * Common sequences have null IDs since they are not persisted.
     * 
     * @return List of common SoloSequence objects with titles but null IDs.
     */
    fun getCommonSequences(): List<SoloSequence> {
        return allSequences
    }

    /**
     * Returns the unique StepUnits used in common sequences.
     * These StepUnits can be used as building blocks for generating new sequences.
     *
     * @return List of unique StepUnit objects extracted from common sequences.
     */
    fun getCommonStepUnits(): List<StepUnit> {
        val allStepUnits = allSequences.flatMap { it.stepUnits }
        // Return unique StepUnits (by structural equality)
        return allStepUnits.distinct()
    }


    // ============================================================================
    // Common Sequences List
    // Add new sequence functions to this list
    // ============================================================================

    private val allSequences: List<SoloSequence> = listOf(
        basicStepInPlace(),
        linearBasicForwardHalf(),
        linearBasicBackwardHalf(),
        lateralStep(),
        reverseLateral()
    )

    // ============================================================================
    // Sequence Functions
    // Add new sequences by creating a function here and adding it to allSequences
    // Internal so they can be referenced by DefaultPartnerSequenceProvider
    // ============================================================================

    internal fun basicStepInPlace() = SoloSequence(
        title = "Basic Step In Place",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.InPlace,
                step2 = Step.InPlace,
                step3 = Step.InPlace
            )
        )
    )
    
    /**
     * Linear Basic Forward Half
     */
    internal fun linearBasicForwardHalf() = SoloSequence(
        title = "Linear Basic Forward Half",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Forward,
                step2 = Step.Forward,
                step3 = Step.Backward,
                dominantStartingFoot = WeightFoot.RIGHT
            )
        )
    )
    
    /**
     * Linear Basic Backward Half
     */
    internal fun linearBasicBackwardHalf() = SoloSequence(
        title = "Linear Basic Backward Half",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Backward,
                step2 = Step.Backward,
                step3 = Step.Forward,
                dominantStartingFoot = WeightFoot.LEFT
            )
        )
    )
    
    /**
     * Lateral Step
     */
    internal fun lateralStep() = SoloSequence(
        title = "Lateral Step",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Forward,
                step2 = Step.Forward,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_180
            )
        )
    )

    /**
     * Reverse Lateral
     */
    internal fun reverseLateral() = SoloSequence(
        title = "Reverse Lateral",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Backward,
                step2 = Step.Backward,
                step3 = Step.Backward,
                rotation = Rotation.ROTATION_180
            )
        )
    )
}
