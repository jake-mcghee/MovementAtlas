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
        reverseLateral(),
        threeStepsForward(),
        threeStepsBackward(),
        sideStep(),
        leadersLateralStep(),
        sideBalanco(),
        forwardsPrepTurn(),
        backwardsPrepTurn(),
        simpleTurnToTheSide(),
        simpleTurnToTheBack(),
        simpleTurnTravelingForward(),
        simpleTurnToTheSideThreeQuarterTurn(),
        patinha(),
        soltinhoInASquarePrep(),
        soltinhoInASquareTurn(),
        abertura(),
        threeStepTurnToTheFront180(),
        threeStepTurnToTheSide180(),
        threeStepTurnToTheSide360()
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
                rotation = Rotation.ROTATION_180,
                rotationGoesInTheDirectionOfTheFirstSteppingFoot = false
            )
        )
    )

    /**
     * Three steps forward
     */
    internal fun threeStepsForward() = SoloSequence(
        title = "Three Steps Forward",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.Forward,
                step3 = Step.Forward
            )
        )
    )

    /**
     * Three steps backward
     */
    internal fun threeStepsBackward() = SoloSequence(
        title = "Three Steps Backward",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Backward,
                step2 = Step.Backward,
                step3 = Step.Backward
            )
        )
    )

    /**
     * Side step
     */
    internal fun sideStep() = SoloSequence(
        title = "Side Step",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Side,
                step2 = Step.Backward,
                step3 = Step.Forward
            )
        )
    )

    /**
     * Leaders lateral step
     */
    internal fun leadersLateralStep() = SoloSequence(
        title = "Leaders Lateral Step",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Side,
                step2 = Step.Forward,
                step3 = Step.Backward
            )
        )
    )

    /**
     * Side balanco
     */
    internal fun sideBalanco() = SoloSequence(
        title = "Side Balanco",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Side,
                step2 = Step.InPlace,
                step3 = Step.Side
            )
        )
    )


    /**
     * Forwards prep Turn
     */
    internal fun forwardsPrepTurn() = SoloSequence(
        title = "Forwards Prep Turn",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.InPlace,
                step3 = Step.Side,
                rotation = Rotation.ROTATION_180,
                dominantStartingFoot = WeightFoot.LEFT
            )
        )
    )


    /**
     * Backwards prep turn
     */
    internal fun backwardsPrepTurn() = SoloSequence(
        title = "Backwards Prep Turn",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Backward,
                step2 = Step.InPlace,
                step3 = Step.Side,
                rotation = Rotation.ROTATION_180,
                dominantStartingFoot = WeightFoot.RIGHT
            )
        )
    )

    /**
     * Simple turn to the side
     */
    internal fun simpleTurnToTheSide() = SoloSequence(
        title = "Simple Turn to the Side",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Side,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_360,
                dominantStartingFoot = WeightFoot.LEFT
            )
        )
    )

    /**
     * Simple turn to the back
     */
    internal fun simpleTurnToTheBack() = SoloSequence(
        title = "Simple Turn to the Back",
        stepUnits = listOf(
            StepUnit.DistanceTwo(
                step1 = Step.Backward,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_360,
                dominantStartingFoot = WeightFoot.RIGHT
            )
        )
    )


    /**
     * Simple turn traveling forward
     */
    internal fun simpleTurnTravelingForward() = SoloSequence(
        title = "Simple Turn Traveling Forward",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.InPlace,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_360
            )
        )
    )

    /**
     * Patinha
     */
    internal fun patinha() = SoloSequence(
        title = "Patinha",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.InPlace,
                step3 = Step.Backward,
                rotation = Rotation.ROTATION_180,
            )
        )
    )

    /**
     * Simple turn to the side 3/4 turn
     */
    internal fun simpleTurnToTheSideThreeQuarterTurn() = SoloSequence(
        title = "Simple Turn to the Side 3/4 Turn",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Side,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_270,
                dominantStartingFoot = WeightFoot.LEFT
            )
        )
    )

    /**
     * Soltinho in square prep
     */
    internal fun soltinhoInASquarePrep() = SoloSequence(
        title = "Soltinho in a Square Prep",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.Forward,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_90,
                dominantStartingFoot = WeightFoot.RIGHT
            )
        )
    )

    /**
     * Soltinho in a square turn
     */
    internal fun soltinhoInASquareTurn() = SoloSequence(
        title = "Soltinho in a Square Turn",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step3 = Step.Forward,
                rotation = Rotation.ROTATION_270,
                dominantStartingFoot = WeightFoot.LEFT
            )
        )
    )

    /**
     * Abertura
     */
    internal fun abertura() = SoloSequence(
        title = "Abertura",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.InPlace,
                step3 = Step.Side,
                rotation = Rotation.ROTATION_270,
                dominantStartingFoot = WeightFoot.RIGHT
            )
        )
    )

    /**
     * Three step turn to the front 180
     */
    internal fun threeStepTurnToTheFront180() = SoloSequence(
        title = "Three Step Turn to the Front 180",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Forward,
                step2 = Step.InPlace,
                step3 = Step.Backward,
                rotation = Rotation.ROTATION_180
            )
        )
    )

    /**
     * Three step turn to the side 180
     */
    internal fun threeStepTurnToTheSide180() = SoloSequence(
        title = "Three Step Turn to the Side 180",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Side,
                step2 = Step.InPlace,
                step3 = Step.Side,
                rotation = Rotation.ROTATION_180
            )
        )
    )


    /**
     * Three step turn to the side 360
     */
    internal fun threeStepTurnToTheSide360() = SoloSequence(
        title = "Three Step Turn to the Side 360",
        stepUnits = listOf(
            StepUnit.DistanceThree(
                step1 = Step.Side,
                step2 = Step.InPlace,
                step3 = Step.Side,
                rotation = Rotation.ROTATION_360
            )
        )
    )


}
