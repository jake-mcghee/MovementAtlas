package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class SoloSequenceTest {

    @Test
    fun `SoloSequence is created with step units`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnits = listOf(stepUnit1, stepUnit2)
        
        // When
        val sequence = SoloSequence(
            id = null,
            stepUnits = stepUnits
        )
        
        // Then
        assertEquals(null, sequence.id)
        assertEquals(stepUnits, sequence.stepUnits)
    }

    @Test
    fun `Empty solo sequence is valid`() {
        // When
        val sequence = SoloSequence(
            id = null,
            stepUnits = emptyList()
        )
        
        // Then
        assertEquals(null, sequence.id)
        assertTrue(sequence.stepUnits.isEmpty())
    }

    @Test
    fun `computeEndWeightFoot computes correct end foot from start foot`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceOne(step = stepPattern)
        val sequence = SoloSequence(
            id = null,
            stepUnits = listOf(stepUnit1, stepUnit2)
        )
        val startWeightFoot = WeightFoot.LEFT
        
        // When
        val endWeightFoot = sequence.computeEndWeightFoot(startWeightFoot)
        
        // Then
        // Two step units, each alternates foot: LEFT -> RIGHT -> LEFT
        assertEquals(WeightFoot.LEFT, endWeightFoot)
    }

    @Test
    fun `computeEndWeightFoot for empty sequence returns start foot`() {
        // Given
        val sequence = SoloSequence(
            id = null,
            stepUnits = emptyList()
        )
        val startWeightFoot = WeightFoot.RIGHT
        
        // When
        val endWeightFoot = sequence.computeEndWeightFoot(startWeightFoot)
        
        // Then
        assertEquals(startWeightFoot, endWeightFoot)
    }
}
