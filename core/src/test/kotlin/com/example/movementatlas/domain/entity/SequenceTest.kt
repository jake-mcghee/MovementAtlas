package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class SequenceTest {

    @Test
    fun `Sequence is created with steps and start and end states`() {
        // Given
        val step1 = Step(
            id = "step-1",
            name = "Step 1",
            tags = emptyList(),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
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
        val steps = listOf(step1, step2)
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        val endState = State.Solo(SoloState(WeightFoot.LEFT))
        
        // When
        val sequence = Sequence(steps, startState, endState)
        
        // Then
        assertEquals(steps, sequence.steps)
        assertEquals(startState, sequence.startState)
        assertEquals(endState, sequence.endState)
    }

    @Test
    fun `Empty sequence is valid`() {
        // Given
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        
        // When
        val sequence = Sequence(emptyList(), startState, startState)
        
        // Then
        assertTrue(sequence.steps.isEmpty())
        assertEquals(startState, sequence.startState)
        assertEquals(startState, sequence.endState)
    }
}
