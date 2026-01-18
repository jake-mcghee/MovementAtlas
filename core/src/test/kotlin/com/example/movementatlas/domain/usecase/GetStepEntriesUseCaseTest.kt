package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepUnitEntriesUseCaseTest {

    @Test
    fun `returns all states that satisfy step unit preconditions`() {
        // Given
        val validState = State.Solo(SoloState(WeightFoot.LEFT))
        val invalidState = State.Solo(SoloState(WeightFoot.RIGHT)) // Invalid because step starts from LEFT

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
            preconditions = listOf(validState, invalidState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val useCase = GetStepUnitEntriesUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - only validState should be returned (invalidState doesn't match step's starting foot)
        assertEquals(1, result.size)
        assertTrue(result.contains(validState))
        assertFalse(result.contains(invalidState))
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

        val useCase = GetStepUnitEntriesUseCase()

        // When
        val result = useCase(stepUnit)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `filters out states that fail type validation`() {
        // Given - a solo state trying to enter a partner step unit
        val soloState = State.Solo(SoloState(WeightFoot.LEFT))
        val partnerState = State.Partner(
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
            name = "Partner Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(soloState, partnerState),
            postState = State.Partner(
                PartnerState(
                    lead = SoloState(WeightFoot.RIGHT),
                    follow = SoloState(WeightFoot.LEFT)
                )
            ),
            type = StepType.PARTNER
        )

        val useCase = GetStepUnitEntriesUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - only the partner state should be valid
        assertEquals(1, result.size)
        assertTrue(result.contains(partnerState))
        assertFalse(result.contains(soloState))
    }
}
