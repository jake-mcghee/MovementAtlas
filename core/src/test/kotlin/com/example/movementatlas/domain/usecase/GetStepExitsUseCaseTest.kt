package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepUnitExitsUseCaseTest {

    @Test
    fun `returns resulting states by applying step unit to all valid entry states`() {
        // Given
        val entryState1 = State.Solo(SoloState(WeightFoot.LEFT))
        val entryState2 = State.Solo(SoloState(WeightFoot.RIGHT)) // Invalid because step starts from LEFT
        val exitState = State.Solo(SoloState(WeightFoot.RIGHT))

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
            preconditions = listOf(entryState1, entryState2),
            postState = exitState,
            type = StepType.SOLO
        )

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - only entryState1 is valid (entryState2 doesn't match step's starting foot)
        assertEquals(1, result.size)
        assertEquals(exitState, result[0])
    }

    @Test
    fun `returns empty list when step unit has no valid entry states`() {
        // Given
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
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `filters out invalid entry states before applying transitions`() {
        // Given - a solo state trying to enter a partner step unit
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
            name = "Partner Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(soloState, partnerState),
            postState = exitState,
            type = StepType.PARTNER
        )

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - only one exit state from the valid partner entry
        assertEquals(1, result.size)
        assertEquals(exitState, result[0])
    }
}
