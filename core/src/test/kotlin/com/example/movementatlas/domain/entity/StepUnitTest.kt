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
            step3 = stepPattern
        )
        
        // Then
        assertEquals(1, singleStepUnit.steps.size)
        assertEquals(3, tripleStepUnit.steps.size) // step2 is computed internally
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
        
        // Verify it computes correctly from LEFT - ends on opposite foot
        assertEquals(WeightFoot.RIGHT, stepUnit.computePostState(WeightFoot.LEFT))
        // Verify it computes correctly from RIGHT - ends on opposite foot
        assertEquals(WeightFoot.LEFT, stepUnit.computePostState(WeightFoot.RIGHT))
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
        
        // When starting from LEFT: ends on opposite foot (RIGHT)
        val resultFromLeft = stepUnit.computePostState(WeightFoot.LEFT)
        // When starting from RIGHT: ends on opposite foot (LEFT)
        val resultFromRight = stepUnit.computePostState(WeightFoot.RIGHT)
        
        // Then - DistanceTwo always ends on opposite foot
        assertEquals(WeightFoot.RIGHT, resultFromLeft)
        assertEquals(WeightFoot.LEFT, resultFromRight)
    }

    @Test
    fun `DistanceTwo stores both step1 and step2 with meaningful directions`() {
        // Given
        val step1 = Step(direction = Direction.FORWARD)
        val step2 = Step(direction = Direction.BACKWARD)
        
        // When
        val stepUnit = StepUnit.DistanceTwo(step1 = step1, step2 = step2)
        
        // Then - both steps are stored and accessible
        assertEquals(2, stepUnit.steps.size)
        assertEquals(step1, stepUnit.steps[0])
        assertEquals(step2, stepUnit.steps[1])
    }

    @Test
    fun `DistanceTwo always ends on opposite foot from starting foot`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)
        
        // When/Then - from LEFT ends on RIGHT
        assertEquals(WeightFoot.RIGHT, stepUnit.computePostState(WeightFoot.LEFT))
        // When/Then - from RIGHT ends on LEFT
        assertEquals(WeightFoot.LEFT, stepUnit.computePostState(WeightFoot.RIGHT))
    }

    @Test
    fun `DistanceThree only stores step1 and step3 step2 is computed internally`() {
        // Given
        val step1 = Step(direction = Direction.FORWARD)
        val step3 = Step(direction = Direction.BACKWARD)
        
        // When
        val stepUnit = StepUnit.DistanceThree(step1 = step1, step3 = step3)
        
        // Then - step2 is computed internally as weight transfer (IN_PLACE)
        assertEquals(3, stepUnit.steps.size)
        assertEquals(step1, stepUnit.steps[0])
        assertEquals(Direction.IN_PLACE, stepUnit.steps[1].direction) // step2 is computed
        assertEquals(step3, stepUnit.steps[2])
    }

    @Test
    fun `DistanceThree step2 direction does not affect equality`() {
        // Given - step2 direction doesn't matter, only step1 and step3 do
        val step1 = Step(direction = Direction.FORWARD)
        val step3 = Step(direction = Direction.BACKWARD)
        
        // When - create two DistanceThree with same step1 and step3
        val stepUnit1 = StepUnit.DistanceThree(step1 = step1, step3 = step3)
        val stepUnit2 = StepUnit.DistanceThree(step1 = step1, step3 = step3)
        
        // Then - they are equal (step2 is computed internally, so always same)
        assertEquals(stepUnit1, stepUnit2)
    }

    @Test
    fun `all StepUnit types end on opposite foot from starting foot`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val distanceOne = StepUnit.DistanceOne(step = stepPattern)
        val distanceTwo = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)
        val distanceThree = StepUnit.DistanceThree(step1 = stepPattern, step3 = stepPattern)
        
        // When/Then - DistanceOne from LEFT ends on RIGHT
        assertEquals(WeightFoot.RIGHT, distanceOne.computePostState(WeightFoot.LEFT))
        // When/Then - DistanceOne from RIGHT ends on LEFT
        assertEquals(WeightFoot.LEFT, distanceOne.computePostState(WeightFoot.RIGHT))
        
        // When/Then - DistanceTwo from LEFT ends on RIGHT
        assertEquals(WeightFoot.RIGHT, distanceTwo.computePostState(WeightFoot.LEFT))
        // When/Then - DistanceTwo from RIGHT ends on LEFT
        assertEquals(WeightFoot.LEFT, distanceTwo.computePostState(WeightFoot.RIGHT))
        
        // When/Then - DistanceThree from LEFT ends on RIGHT
        assertEquals(WeightFoot.RIGHT, distanceThree.computePostState(WeightFoot.LEFT))
        // When/Then - DistanceThree from RIGHT ends on LEFT
        assertEquals(WeightFoot.LEFT, distanceThree.computePostState(WeightFoot.RIGHT))
    }
}
