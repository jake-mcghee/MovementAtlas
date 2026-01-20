package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepTest {

    @Test
    fun `Step has direction property`() {
        // When
        val step = Step.InPlace
        
        // Then
        assertEquals(Direction.IN_PLACE, step.direction)
    }

    @Test
    fun `Step endingFoot is always opposite of startingFoot`() {
        // Given
        val step = Step.InPlace
        
        // When/Then
        assertEquals(WeightFoot.RIGHT, step.endingFoot(WeightFoot.LEFT))
        assertEquals(WeightFoot.LEFT, step.endingFoot(WeightFoot.RIGHT))
    }

    @Test
    fun `Step equality is based on instance`() {
        // Given
        val step1 = Step.InPlace
        val step2 = Step.InPlace
        val step3 = Step.Forward
        
        // Then
        assertEquals(step1, step2) // Same instance = equal
        assertNotEquals(step1, step3) // Different instance = not equal
    }
    
    @Test
    fun `Step from creates correct Step from Direction`() {
        // When/Then
        assertEquals(Step.InPlace, Step.from(Direction.IN_PLACE))
        assertEquals(Step.Forward, Step.from(Direction.FORWARD))
        assertEquals(Step.Backward, Step.from(Direction.BACKWARD))
        assertEquals(Step.Left, Step.from(Direction.LEFT))
        assertEquals(Step.Right, Step.from(Direction.RIGHT))
    }
}
