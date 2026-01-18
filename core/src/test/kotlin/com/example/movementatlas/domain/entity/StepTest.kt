package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepTest {

    @Test
    fun `Step pattern is created with direction only`() {
        // Given
        val direction = Direction.IN_PLACE
        
        // When
        val step = Step(direction = direction)
        
        // Then
        assertEquals(direction, step.direction)
    }

    @Test
    fun `Step endingFoot is always opposite of startingFoot`() {
        // Given
        val step = Step(direction = Direction.IN_PLACE)
        
        // When/Then
        assertEquals(WeightFoot.RIGHT, step.endingFoot(WeightFoot.LEFT))
        assertEquals(WeightFoot.LEFT, step.endingFoot(WeightFoot.RIGHT))
    }

    @Test
    fun `Step equality is based on direction`() {
        // Given
        val step1 = Step(direction = Direction.IN_PLACE)
        val step2 = Step(direction = Direction.IN_PLACE)
        val step3 = Step(direction = Direction.FORWARD)
        
        // Then
        assertEquals(step1, step2) // Same direction = equal
        assertNotEquals(step1, step3) // Different direction = not equal
    }
}
