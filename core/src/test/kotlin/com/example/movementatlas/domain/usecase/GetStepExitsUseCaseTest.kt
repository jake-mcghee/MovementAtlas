package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepExitsUseCaseTest {

    @Test
    fun `returns resulting states by applying step to all valid entry states`() {
        // Given
        val entryState1 = State.Solo(SoloState(WeightFoot.LEFT))
        val entryState2 = State.Solo(SoloState(WeightFoot.RIGHT))
        val exitState = State.Solo(SoloState(WeightFoot.LEFT))

        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(entryState1, entryState2),
            postState = exitState,
            type = StepType.SOLO
        )

        val useCase = GetStepExitsUseCase()

        // When
        val result = useCase(step)

        // Then
        assertEquals(2, result.size)
        assertTrue(result.all { it == exitState })
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

        val useCase = GetStepExitsUseCase()

        // When
        val result = useCase(step)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `filters out invalid entry states before applying transitions`() {
        // Given - a solo state trying to enter a partner step
        val soloState = State.Solo(SoloState(WeightFoot.LEFT))
        val partnerState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.LEFT),
                follow = SoloState(WeightFoot.RIGHT)
            )
        )
        val exitState = State.Partner(
            PartnerState(
                lead = SoloState(WeightFoot.RIGHT),
                follow = SoloState(WeightFoot.LEFT)
            )
        )

        val step = Step(
            id = "step-1",
            name = "Partner Step",
            tags = emptyList(),
            preconditions = listOf(soloState, partnerState),
            postState = exitState,
            type = StepType.PARTNER
        )

        val useCase = GetStepExitsUseCase()

        // When
        val result = useCase(step)

        // Then - only one exit state from the valid partner entry
        assertEquals(1, result.size)
        assertEquals(exitState, result[0])
    }
}
