package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StateTest {

    @Test
    fun `Solo state validates solo step unit transitions with matching weight foot`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(fromState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(stepUnit)

        // Then
        assertTrue(canTransition)
    }

    @Test
    fun `Solo state rejects solo step unit transitions with non-matching weight foot`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-rl",
            name = "Right to Left",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.RIGHT,
            weightFootTo = WeightFoot.LEFT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(stepUnit)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Solo state rejects partner step unit transitions`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-partner",
            name = "Partner Step",
            tags = emptyList(),
            type = StepType.PARTNER,
            leadFrom = WeightFoot.LEFT,
            leadTo = WeightFoot.RIGHT,
            followFrom = WeightFoot.RIGHT,
            followTo = WeightFoot.LEFT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(
                State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.LEFT),
                        follow = SoloState(WeightFoot.RIGHT)
                    )
                )
            ),
            postState = State.Partner(
                PartnerState(
                    lead = SoloState(WeightFoot.RIGHT),
                    follow = SoloState(WeightFoot.LEFT)
                )
            ),
            type = StepType.PARTNER
        )

        // When
        val canTransition = fromState.canTransitionTo(stepUnit)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Partner state validates partner step unit transitions with matching state type`() {
        // Given
        val fromState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val step = Step(
            id = "step-partner",
            name = "Partner Step",
            tags = emptyList(),
            type = StepType.PARTNER,
            leadFrom = WeightFoot.LEFT,
            leadTo = WeightFoot.RIGHT,
            followFrom = WeightFoot.RIGHT,
            followTo = WeightFoot.LEFT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(fromState),
            postState = State.Partner(
                PartnerState(
                    lead = SoloState(WeightFoot.RIGHT),
                    follow = SoloState(WeightFoot.LEFT)
                )
            ),
            type = StepType.PARTNER
        )

        // When
        val canTransition = fromState.canTransitionTo(stepUnit)

        // Then
        assertTrue(canTransition)
    }

    @Test
    fun `Partner state rejects solo step unit transitions`() {
        // Given
        val fromState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val step = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(stepUnit)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Solo state applies transition correctly`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val expectedPostState = State.Solo(SoloState(WeightFoot.RIGHT))
        val step = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(fromState),
            postState = expectedPostState,
            type = StepType.SOLO
        )

        // When
        val result = fromState.applyTransition(stepUnit)

        // Then
        assertEquals(expectedPostState, result)
    }

    @Test
    fun `Partner state applies transition correctly`() {
        // Given
        val fromState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val expectedPostState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.RIGHT),
                follow = SoloState(WeightFoot.LEFT)
            )
        )
        val step = Step(
            id = "step-partner",
            name = "Partner Step",
            tags = emptyList(),
            type = StepType.PARTNER,
            leadFrom = WeightFoot.LEFT,
            leadTo = WeightFoot.RIGHT,
            followFrom = WeightFoot.RIGHT,
            followTo = WeightFoot.LEFT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(fromState),
            postState = expectedPostState,
            type = StepType.PARTNER
        )

        // When
        val result = fromState.applyTransition(stepUnit)

        // Then
        assertEquals(expectedPostState, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `applyTransition throws exception for invalid transition`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-rl",
            name = "Right to Left",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.RIGHT,
            weightFootTo = WeightFoot.LEFT
        )
        val stepUnit = StepUnit(
            id = "unit-1",
            name = "Test Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        // When/Then - should throw
        fromState.applyTransition(stepUnit)
    }
}
