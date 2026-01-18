package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StateTransitionRules
import io.mockk.coEvery
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
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(any(), any()) } returns false
            every { isValidTransition(startState, step1) } returns true
            every { isValidTransition(State.Solo(SoloState(WeightFoot.RIGHT)), step2) } returns true
            every { applyTransition(startState, step1) } returns State.Solo(SoloState(WeightFoot.RIGHT))
            every { applyTransition(State.Solo(SoloState(WeightFoot.RIGHT)), step2) } returns State.Solo(SoloState(WeightFoot.LEFT))
        }
        
        val useCase = GenerateSequencesUseCase(stepRepository, transitionRules)
        
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
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(any(), any()) } returns true
            every { applyTransition(startState, step) } returns State.Solo(SoloState(WeightFoot.RIGHT))
        }
        
        val useCase = GenerateSequencesUseCase(stepRepository, transitionRules)
        
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
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(any(), any()) } returns false
            every { isValidTransition(startState, validStep) } returns true
            every { applyTransition(startState, validStep) } returns State.Solo(SoloState(WeightFoot.RIGHT))
        }
        
        val useCase = GenerateSequencesUseCase(stepRepository, transitionRules)
        
        // When
        val result = useCase(startState, 5).first()
        
        // Then
        result.forEach { sequence ->
            sequence.steps.forEach { step ->
                assertNotEquals(invalidStep.id, step.id)
            }
        }
    }
}
