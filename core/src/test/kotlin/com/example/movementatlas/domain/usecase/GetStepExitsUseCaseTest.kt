package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StateTransitionRules
import io.mockk.every
import io.mockk.mockk
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
        
        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(entryState1, step) } returns true
            every { isValidTransition(entryState2, step) } returns true
            every { applyTransition(entryState1, step) } returns exitState
            every { applyTransition(entryState2, step) } returns exitState
        }
        
        val useCase = GetStepExitsUseCase(transitionRules)
        
        // When
        val result = useCase(step)
        
        // Then
        assertEquals(2, result.size)
        assertTrue(result.contains(exitState))
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
        
        val transitionRules = mockk<StateTransitionRules>()
        
        val useCase = GetStepExitsUseCase(transitionRules)
        
        // When
        val result = useCase(step)
        
        // Then
        assertTrue(result.isEmpty())
    }
}
