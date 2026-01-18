package com.example.movementatlas.domain.service

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class StateTransitionRulesImplTest {

    private val rules = StateTransitionRulesImpl()

    @Test
    fun `validates solo step transitions with matching weight foot`() {
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
        val isValid = rules.isValidTransition(fromState, step)

        // Then
        assertTrue(isValid)
    }

    @Test
    fun `rejects solo step transitions with non-matching weight foot`() {
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
        val isValid = rules.isValidTransition(fromState, step)

        // Then
        assertFalse(isValid)
    }

    @Test
    fun `validates partner step transitions with matching state type`() {
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
        val isValid = rules.isValidTransition(fromState, step)

        // Then
        assertTrue(isValid)
    }

    @Test
    fun `rejects partner step transitions with non-matching state type`() {
        // Given
        val fromState = State.Solo(SoloState(WeightFoot.LEFT))
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
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
        val isValid = rules.isValidTransition(fromState, step)

        // Then
        assertFalse(isValid)
    }

    @Test
    fun `applies transition correctly for solo step`() {
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
        val result = rules.applyTransition(fromState, step)

        // Then
        assertEquals(step.postState, result)
    }

    @Test
    fun `applies transition correctly for partner step`() {
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
        val result = rules.applyTransition(fromState, step)

        // Then
        assertEquals(step.postState, result)
    }
}
