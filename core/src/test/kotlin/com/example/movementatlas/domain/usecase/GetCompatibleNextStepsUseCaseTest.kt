package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StateTransitionRules
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetCompatibleNextStepsUseCaseTest {

    @Test
    fun `returns only steps that are valid transitions from given state`() = runTest {
        // Given
        val currentState = State.Solo(SoloState(WeightFoot.LEFT))
        
        val compatibleStep = Step(
            id = "step-1",
            name = "Compatible Step",
            tags = emptyList(),
            preconditions = listOf(currentState),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
        
        val incompatibleStep = Step(
            id = "step-2",
            name = "Incompatible Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
        
        val allSteps = listOf(compatibleStep, incompatibleStep)
        
        val stepRepository = mockk<StepRepository> {
            every { getAllSteps() } returns flowOf(allSteps)
        }
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(currentState, compatibleStep) } returns true
            every { isValidTransition(currentState, incompatibleStep) } returns false
        }
        
        val useCase = GetCompatibleNextStepsUseCase(stepRepository, transitionRules)
        
        // When
        val result = useCase(currentState)
        
        // Then
        assertEquals(listOf(compatibleStep), result)
    }

    @Test
    fun `returns empty list when no steps are compatible`() = runTest {
        // Given
        val currentState = State.Solo(SoloState(WeightFoot.LEFT))
        
        val step = Step(
            id = "step-1",
            name = "Step",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
        
        val stepRepository = mockk<StepRepository> {
            every { getAllSteps() } returns flowOf(listOf(step))
        }
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(currentState, step) } returns false
        }
        
        val useCase = GetCompatibleNextStepsUseCase(stepRepository, transitionRules)
        
        // When
        val result = useCase(currentState)
        
        // Then
        assertTrue(result.isEmpty())
    }
}
