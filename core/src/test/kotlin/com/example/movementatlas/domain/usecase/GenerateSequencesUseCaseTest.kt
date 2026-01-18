package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepUnitRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GenerateSequencesUseCaseTest {

    @Test
    fun `generates sequences from start state respecting maxLength`() = runTest {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        val maxLength = 2

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

        val stepUnit1 = StepUnit(
            id = "unit-1",
            name = "Unit 1",
            tags = emptyList(),
            steps = listOf(step1),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val stepUnit2 = StepUnit(
            id = "unit-2",
            name = "Unit 2",
            tags = emptyList(),
            steps = listOf(step2),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val allStepUnits = listOf(stepUnit1, stepUnit2)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(allStepUnits)
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startState, maxLength).first()

        // Then
        assertTrue(result.isNotEmpty())
        result.forEach { sequence ->
            assertTrue(sequence.stepUnits.size <= maxLength)
            assertEquals(startState, sequence.startState)
        }
    }

    @Test
    fun `respects maxLength parameter`() = runTest {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        val maxLength = 1

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
            name = "Unit 1",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit))
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startState, maxLength).first()

        // Then
        result.forEach { sequence ->
            assertTrue(sequence.stepUnits.size <= maxLength)
        }
    }

    @Test
    fun `only includes valid transitions`() = runTest {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))

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

        val validStepUnit = StepUnit(
            id = "unit-1",
            name = "Valid Unit",
            tags = emptyList(),
            steps = listOf(step1),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val invalidStepUnit = StepUnit(
            id = "unit-2",
            name = "Invalid Unit",
            tags = emptyList(),
            steps = listOf(step2),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(validStepUnit, invalidStepUnit))
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startState, 1).first()

        // Then
        // At depth 1, only validStepUnit should be in first-level sequences
        val firstLevelStepUnits = result.filter { it.stepUnits.size == 1 }.flatMap { it.stepUnits }
        assertTrue(firstLevelStepUnits.all { it.id == validStepUnit.id })
    }
}
