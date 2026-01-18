package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class SoloStateTest {

    @Test
    fun `SoloState with LEFT weight foot is created correctly`() {
        // Given
        val weightFoot = WeightFoot.LEFT
        
        // When
        val state = SoloState(weightFoot)
        
        // Then
        assertEquals(WeightFoot.LEFT, state.weightFoot)
    }

    @Test
    fun `SoloState with RIGHT weight foot is created correctly`() {
        // Given
        val weightFoot = WeightFoot.RIGHT
        
        // When
        val state = SoloState(weightFoot)
        
        // Then
        assertEquals(WeightFoot.RIGHT, state.weightFoot)
    }

    @Test
    fun `SoloState equality is based on weightFoot`() {
        // Given
        val state1 = SoloState(WeightFoot.LEFT)
        val state2 = SoloState(WeightFoot.LEFT)
        val state3 = SoloState(WeightFoot.RIGHT)
        
        // Then
        assertEquals(state1, state2)
        assertNotEquals(state1, state3)
    }
}
