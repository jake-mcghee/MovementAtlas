package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class SequenceTest {

    @Test
    fun `Sequence is created with step units and start and end states`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnits = listOf(stepUnit1, stepUnit2)
        val startWeightFoot = WeightFoot.LEFT
        val endWeightFoot = WeightFoot.LEFT
        
        // When
        val sequence = Sequence(stepUnits, startWeightFoot, endWeightFoot)
        
        // Then
        assertEquals(stepUnits, sequence.stepUnits)
        assertEquals(startWeightFoot, sequence.startWeightFoot)
        assertEquals(endWeightFoot, sequence.endWeightFoot)
    }

    @Test
    fun `Empty sequence is valid`() {
        // Given
        val startWeightFoot = WeightFoot.LEFT
        
        // When
        val sequence = Sequence(emptyList(), startWeightFoot, startWeightFoot)
        
        // Then
        assertTrue(sequence.stepUnits.isEmpty())
        assertEquals(startWeightFoot, sequence.startWeightFoot)
        assertEquals(startWeightFoot, sequence.endWeightFoot)
    }
}
