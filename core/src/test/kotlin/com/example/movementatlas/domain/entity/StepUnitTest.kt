package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepUnitTest {

    @Test
    fun `StepUnit is created with 1-3 steps`() {
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
        val step3 = Step(
            id = "step-lr2",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        
        // When
        val singleStepUnit = StepUnit(
            id = "unit-1",
            name = "Single Step",
            tags = emptyList(),
            steps = listOf(step1),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
        val tripleStepUnit = StepUnit(
            id = "unit-2",
            name = "Triple Step",
            tags = emptyList(),
            steps = listOf(step1, step2, step3),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
        
        // Then
        assertEquals(1, singleStepUnit.steps.size)
        assertEquals(3, tripleStepUnit.steps.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `StepUnit rejects empty steps`() {
        // When/Then - should throw
        StepUnit(
            id = "unit-1",
            name = "Empty Unit",
            tags = emptyList(),
            steps = emptyList(),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `StepUnit rejects more than 3 steps`() {
        // Given
        val step = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        
        // When/Then - should throw
        StepUnit(
            id = "unit-1",
            name = "Too Many Steps",
            tags = emptyList(),
            steps = listOf(step, step, step, step),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `StepUnit rejects steps that do not chain correctly`() {
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
            id = "step-lr2",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT, // Should be RIGHT to chain from step1
            weightFootTo = WeightFoot.RIGHT
        )
        
        // When/Then - should throw
        StepUnit(
            id = "unit-1",
            name = "Invalid Chain",
            tags = emptyList(),
            steps = listOf(step1, step2),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
    }

    @Test
    fun `StepUnit equality is based on id`() {
        // Given
        val step = Step(
            id = "step-lr",
            name = "Left to Right",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val stepUnit1 = StepUnit(
            id = "unit-1",
            name = "Unit 1",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
        val stepUnit2 = StepUnit(
            id = "unit-1",
            name = "Different Name",
            tags = listOf("tag"),
            steps = listOf(step),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
        val stepUnit3 = StepUnit(
            id = "unit-2",
            name = "Unit 1",
            tags = emptyList(),
            steps = listOf(step),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        )
        
        // Then
        assertEquals(stepUnit1, stepUnit2)
        assertNotEquals(stepUnit1, stepUnit3)
    }
}
