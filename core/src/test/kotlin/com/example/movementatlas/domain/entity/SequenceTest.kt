package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class SequenceTest {

    @Test
    fun `Sequence is created with step units and start and end states`() {
        // Given
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
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
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
        val stepUnits = listOf(stepUnit1, stepUnit2)
        val startState = State.Solo(SoloState(WeightFoot.LEFT))
        val endState = State.Solo(SoloState(WeightFoot.LEFT))
        
        // When
        val sequence = Sequence(stepUnits, startState, endState)
        
        // Then
        assertEquals(stepUnits, sequence.stepUnits)
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
        assertTrue(sequence.stepUnits.isEmpty())
        assertEquals(startState, sequence.startState)
        assertEquals(startState, sequence.endState)
    }
}
