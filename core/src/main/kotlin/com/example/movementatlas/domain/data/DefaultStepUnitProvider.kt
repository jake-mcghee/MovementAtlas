package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of common step units (hardcoded curated step units).
 * These are domain data that can be used by any platform implementation.
 * 
 * Common step units are separate from user-saved sequences (which are stored via SequenceRepository).
 * Step units are the building blocks for sequences.
 * 
 * To add a new common step unit:
 * 1. Create a function (e.g., `myNewStepUnit()`) that returns a StepUnit
 * 2. Add it to the [allStepUnits] list
 */
object DefaultStepUnitProvider {

    /**
     * Returns a list of common step units.
     * Each step unit represents a well-known movement pattern that can be reused.
     *
     * @return List of common StepUnit objects with titles.
     */
    fun getCommonStepUnits(): List<StepUnit> {
        return allStepUnits
    }

    // ============================================================================
    // Common Step Units List
    // Add new step unit functions to this list
    // ============================================================================

    private val allStepUnits: List<StepUnit> = listOf(
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
    // Step Unit Functions
    // Add new step units by creating a function here and adding it to allStepUnits
    // Internal so they can be referenced by DefaultSoloSequenceProvider and DefaultPartnerSequenceProvider
    // ============================================================================

    internal fun basicStepInPlace() = StepUnit.DistanceTwo(
        step1 = Step.InPlace,
        step2 = Step.InPlace,
        step3 = Step.InPlace,
        title = "Basic Step In Place"
    )

    internal fun linearBasicForwardHalf() = StepUnit.DistanceTwo(
        step1 = Step.Forward,
        step2 = Step.Forward,
        step3 = Step.Backward,
        dominantStartingFoot = WeightFoot.RIGHT,
        title = "Linear Basic Forward Half"
    )

    internal fun linearBasicBackwardHalf() = StepUnit.DistanceTwo(
        step1 = Step.Backward,
        step2 = Step.Backward,
        step3 = Step.Forward,
        dominantStartingFoot = WeightFoot.LEFT,
        title = "Linear Basic Backward Half"
    )

    internal fun lateralStep() = StepUnit.DistanceTwo(
        step1 = Step.Forward,
        step2 = Step.Forward,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_180,
        title = "Lateral Step"
    )

    internal fun reverseLateral() = StepUnit.DistanceTwo(
        step1 = Step.Backward,
        step2 = Step.Backward,
        step3 = Step.Backward,
        rotation = Rotation.ROTATION_180,
        rotationGoesInTheDirectionOfTheFirstSteppingFoot = false,
        title = "Reverse Lateral"
    )

    internal fun threeStepsForward() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.Forward,
        step3 = Step.Forward,
        title = "Three Steps Forward"
    )

    internal fun threeStepsBackward() = StepUnit.DistanceThree(
        step1 = Step.Backward,
        step2 = Step.Backward,
        step3 = Step.Backward,
        title = "Three Steps Backward"
    )

    internal fun sideStep() = StepUnit.DistanceTwo(
        step1 = Step.Side,
        step2 = Step.Backward,
        step3 = Step.Forward,
        title = "Side Step"
    )

    internal fun leadersLateralStep() = StepUnit.DistanceTwo(
        step1 = Step.Side,
        step2 = Step.Forward,
        step3 = Step.Backward,
        title = "Leaders Lateral Step"
    )

    internal fun sideBalanco() = StepUnit.DistanceThree(
        step1 = Step.Side,
        step2 = Step.InPlace,
        step3 = Step.Side,
        title = "Side Balanco"
    )

    internal fun forwardsPrepTurn() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.InPlace,
        step3 = Step.Side,
        rotation = Rotation.ROTATION_180,
        dominantStartingFoot = WeightFoot.LEFT,
        title = "Forwards Prep Turn"
    )

    internal fun backwardsPrepTurn() = StepUnit.DistanceThree(
        step1 = Step.Backward,
        step2 = Step.InPlace,
        step3 = Step.Side,
        rotation = Rotation.ROTATION_180,
        dominantStartingFoot = WeightFoot.RIGHT,
        title = "Backwards Prep Turn"
    )

    internal fun simpleTurnToTheSide() = StepUnit.DistanceTwo(
        step1 = Step.Side,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_360,
        dominantStartingFoot = WeightFoot.LEFT,
        title = "Simple Turn to the Side"
    )

    internal fun simpleTurnToTheBack() = StepUnit.DistanceTwo(
        step1 = Step.Backward,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_360,
        dominantStartingFoot = WeightFoot.RIGHT,
        title = "Simple Turn to the Back"
    )

    internal fun simpleTurnTravelingForward() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.InPlace,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_360,
        title = "Simple Turn Traveling Forward"
    )

    internal fun simpleTurnToTheSideThreeQuarterTurn() = StepUnit.DistanceThree(
        step1 = Step.Side,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_270,
        dominantStartingFoot = WeightFoot.LEFT,
        title = "Simple Turn to the Side 3/4 Turn"
    )

    internal fun patinha() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.InPlace,
        step3 = Step.Backward,
        rotation = Rotation.ROTATION_180,
        title = "Patinha"
    )

    internal fun soltinhoInASquarePrep() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.Forward,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_90,
        dominantStartingFoot = WeightFoot.RIGHT,
        title = "Soltinho in a Square Prep"
    )

    internal fun soltinhoInASquareTurn() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step3 = Step.Forward,
        rotation = Rotation.ROTATION_270,
        dominantStartingFoot = WeightFoot.LEFT,
        title = "Soltinho in a Square Turn"
    )

    internal fun abertura() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.InPlace,
        step3 = Step.Side,
        rotation = Rotation.ROTATION_270,
        dominantStartingFoot = WeightFoot.RIGHT,
        title = "Abertura"
    )

    internal fun threeStepTurnToTheFront180() = StepUnit.DistanceThree(
        step1 = Step.Forward,
        step2 = Step.InPlace,
        step3 = Step.Backward,
        rotation = Rotation.ROTATION_180,
        title = "Three Step Turn to the Front 180"
    )

    internal fun threeStepTurnToTheSide180() = StepUnit.DistanceThree(
        step1 = Step.Side,
        step2 = Step.InPlace,
        step3 = Step.Side,
        rotation = Rotation.ROTATION_180,
        title = "Three Step Turn to the Side 180"
    )

    internal fun threeStepTurnToTheSide360() = StepUnit.DistanceThree(
        step1 = Step.Side,
        step2 = Step.InPlace,
        step3 = Step.Side,
        rotation = Rotation.ROTATION_360,
        title = "Three Step Turn to the Side 360"
    )
}
