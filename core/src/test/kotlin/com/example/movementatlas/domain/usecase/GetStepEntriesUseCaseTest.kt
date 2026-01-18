package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StateTransitionRules
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class GetStepEntriesUseCaseTest {

    @Test
    fun `returns all states that satisfy step preconditions`() {
        // Given
        val validState = State.Solo(SoloState(WeightFoot.LEFT))
        val invalidState = State.Solo(SoloState(WeightFoot.RIGHT))

        val step = Step(
            id = "step-1",
            name = "Test Step",
            tags = emptyList(),
            preconditions = listOf(validState, invalidState),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )

        val transitionRules = mockk<StateTransitionRules> {
            every { isValidTransition(validState, step) } returns true
            every { isValidTransition(invalidState, step) } returns false
        }

        val useCase = GetStepEntriesUseCase(transitionRules)

        // When
        val result = useCase(step)

        // Then
        assertEquals(1, result.size)
        assertTrue(result.contains(validState))
        assertFalse(result.contains(invalidState))
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
        
        val useCase = GetStepEntriesUseCase(transitionRules)
        
        // When
        val result = useCase(step)
        
        // Then
        assertTrue(result.isEmpty())
    }
}
