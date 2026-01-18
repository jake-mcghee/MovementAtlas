package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepRepository
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
            id = "step-1",
            name = "Step 1",
            tags = emptyList(),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val step2 = Step(
            id = "step-2",
            name = "Step 2",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val allSteps = listOf(step1, step2)

        val stepRepository = mockk<StepRepository> {
            every { getAllSteps() } returns flowOf(allSteps)
        }

        val useCase = GenerateSequencesUseCase(stepRepository)

        // When
        val result = useCase(startState, maxLength).first()

        // Then
        assertTrue(result.isNotEmpty())
        result.forEach { sequence ->
            assertTrue(sequence.steps.size <= maxLength)
            assertEquals(startState, sequence.startState)
        }
    }

    @Test
    fun `respects maxLength parameter`() = runTest {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        val maxLength = 1

        val step = Step(
            id = "step-1",
            name = "Step 1",
            tags = emptyList(),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val stepRepository = mockk<StepRepository> {
            every { getAllSteps() } returns flowOf(listOf(step))
        }

        val useCase = GenerateSequencesUseCase(stepRepository)

        // When
        val result = useCase(startState, maxLength).first()

        // Then
        result.forEach { sequence ->
            assertTrue(sequence.steps.size <= maxLength)
        }
    }

    @Test
    fun `only includes valid transitions`() = runTest {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))

        val validStep = Step(
            id = "step-1",
            name = "Valid Step",
            tags = emptyList(),
            preconditions = listOf(startState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )

        val invalidStep = Step(
            id = "step-2",
            name = "Invalid Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val stepRepository = mockk<StepRepository> {
            every { getAllSteps() } returns flowOf(listOf(validStep, invalidStep))
        }

        val useCase = GenerateSequencesUseCase(stepRepository)

        // When
        val result = useCase(startState, 1).first()

        // Then
        // At depth 1, only validStep should be in first-level sequences
        val firstLevelSteps = result.filter { it.steps.size == 1 }.flatMap { it.steps }
        assertTrue(firstLevelSteps.all { it.id == validStep.id })
    }
}
