package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepEntriesUseCaseTest {

    @Test
    fun `returns all states that satisfy step preconditions`() {
        // Given
        val validState = State.Solo(SoloState(WeightFoot.LEFT))
        val anotherValidState = State.Solo(SoloState(WeightFoot.RIGHT))

        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(validState, anotherValidState),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val useCase = GetStepEntriesUseCase()

        // When
        val result = useCase(step)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.contains(validState))
        assertTrue(result.contains(anotherValidState))
    }

    @Test
    fun `returns empty list when step has no valid entry states`() {
        // Given
        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val useCase = GetStepEntriesUseCase()

        // When
        val result = useCase(step)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `filters out states that fail type validation`() {
        // Given - a solo state trying to enter a partner step
        val soloState = State.Solo(SoloState(WeightFoot.LEFT))
        val partnerState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )

        val step = Step(
            id = "step-1",
            name = "Partner Step",
            tags = emptyList(),
            preconditions = listOf(soloState, partnerState),
            postState = State.Partner(
                PartnerState(
                    lead = SoloState(WeightFoot.RIGHT),
                    follow = SoloState(WeightFoot.LEFT)
                )
            ),
            type = StepType.PARTNER
        )

        val useCase = GetStepEntriesUseCase()

        // When
        val result = useCase(step)

        // Then - only the partner state should be valid
        assertEquals(1, result.size)
        assertTrue(result.contains(partnerState))
        assertFalse(result.contains(soloState))
    }
}
