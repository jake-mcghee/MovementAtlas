package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StateTest {

    @Test
    fun `Solo state validates solo step transitions with matching weight foot`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(fromState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(step)

        // Then
        assertTrue(canTransition)
    }

    @Test
    fun `Solo state rejects solo step transitions with non-matching weight foot`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(step)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Solo state rejects partner step transitions`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
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
        val canTransition = fromState.canTransitionTo(step)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Partner state validates partner step transitions with matching state type`() {
        // Given
        val fromState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
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
        val canTransition = fromState.canTransitionTo(step)

        // Then
        assertTrue(canTransition)
    }

    @Test
    fun `Partner state rejects solo step transitions`() {
        // Given
        val fromState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        // When
        val canTransition = fromState.canTransitionTo(step)

        // Then
        assertFalse(canTransition)
    }

    @Test
    fun `Solo state applies transition correctly`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val expectedPostState = State.Solo(SoloState(WeightFoot.RIGHT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(fromState),
            postState = expectedPostState,
            type = StepType.SOLO
        )

        // When
        val result = fromState.applyTransition(step)

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
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(fromState),
            postState = expectedPostState,
            type = StepType.PARTNER
        )

        // When
        val result = fromState.applyTransition(step)

        // Then
        assertEquals(expectedPostState, result)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `applyTransition throws exception for invalid transition`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        // When/Then - should throw
        fromState.applyTransition(step)
    }
}
