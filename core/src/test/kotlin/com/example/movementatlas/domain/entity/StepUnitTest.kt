package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepUnitTest {

    @Test
    fun `StepUnit is created with 1-3 steps`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        
        // When
        val singleStepUnit = StepUnit.DistanceOne(step = stepPattern)
        val tripleStepUnit = StepUnit.DistanceThree(
            step1 = stepPattern,
            step2 = stepPattern,
            step3 = stepPattern
        )
        
        // Then
        assertEquals(1, singleStepUnit.steps.size)
        assertEquals(3, tripleStepUnit.steps.size)
    }

    @Test
    fun `StepUnit patterns can be created with any step patterns`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        
        // When/Then - patterns always chain correctly when applied
        val stepUnit = StepUnit.DistanceTwo(
            step1 = stepPattern,
            step2 = stepPattern
        )
        
        // Verify it computes correctly from LEFT
        assertEquals(WeightFoot.LEFT, stepUnit.computePostState(WeightFoot.LEFT))
        // Verify it computes correctly from RIGHT
        assertEquals(WeightFoot.RIGHT, stepUnit.computePostState(WeightFoot.RIGHT))
    }

    @Test
    fun `StepUnit equality is based on all properties`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val differentStepPattern = Step(direction = Direction.FORWARD)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit3 = StepUnit.DistanceOne(step = differentStepPattern)
        
        // Then
        assertEquals(stepUnit1, stepUnit2) // Same properties = equal
        assertNotEquals(stepUnit1, stepUnit3) // Different properties = not equal
    }

    @Test
    fun `StepUnit canTransitionFrom works for any foot`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)
        
        // When/Then - patterns can be applied from either foot
        assertTrue(stepUnit.canTransitionFrom(WeightFoot.LEFT))
        assertTrue(stepUnit.canTransitionFrom(WeightFoot.RIGHT))
    }

    @Test
    fun `StepUnit computePostState calculates correctly`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceTwo(
            step1 = stepPattern,
            step2 = stepPattern
        )
        
        // When starting from LEFT: L -> R -> L
        val resultFromLeft = stepUnit.computePostState(WeightFoot.LEFT)
        // When starting from RIGHT: R -> L -> R
        val resultFromRight = stepUnit.computePostState(WeightFoot.RIGHT)
        
        // Then
        assertEquals(WeightFoot.LEFT, resultFromLeft)
        assertEquals(WeightFoot.RIGHT, resultFromRight)
    }
}
