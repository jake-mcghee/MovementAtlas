package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepUnitRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetCompatibleNextStepUnitsUseCaseTest {

    @Test
    fun `returns only step units that are valid transitions from given state`() = runTest {
        // Given
        val currentState = State.Solo(SoloState(WeightFoot.LEFT))

        val step1 = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val step2 = Step(
            id = "step-rl",
            name = "Right to Left",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.RIGHT,
            weightFootTo = WeightFoot.LEFT
        )

        val compatibleStepUnit = StepUnit(
            id = "unit-1",
            name = "Compatible Unit",
            tags = emptyList(),
            steps = listOf(step1),
            preconditions = listOf(currentState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val incompatibleStepUnit = StepUnit(
            id = "unit-2",
            name = "Incompatible Unit",
            tags = emptyList(),
            steps = listOf(step2),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val allStepUnits = listOf(compatibleStepUnit, incompatibleStepUnit)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(allStepUnits)
        }

        val useCase = GetCompatibleNextStepUnitsUseCase(stepUnitRepository)

        // When
        val result = useCase(currentState)

        // Then
        assertEquals(listOf(compatibleStepUnit), result)
    }

    @Test
    fun `returns empty list when no step units are compatible`() = runTest {
        // Given
        val currentState = State.Solo(SoloState(WeightFoot.LEFT))

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
            name = "Unit",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit))
        }

        val useCase = GetCompatibleNextStepUnitsUseCase(stepUnitRepository)

        // When
        val result = useCase(currentState)

        // Then
        assertTrue(result.isEmpty())
    }
}
