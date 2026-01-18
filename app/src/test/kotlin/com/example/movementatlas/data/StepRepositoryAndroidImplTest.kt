package com.example.movementatlas.data

import com.example.movementatlas.domain.entity.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class StepRepositoryAndroidImplTest {

    @Test
    fun `returns all steps`() = runTest {
        // Given
        val repository = StepRepositoryAndroidImpl()
        
        // When
        val result = repository.getAllSteps().first()
        
        // Then
        assertTrue(result.isNotEmpty())
        // Verify steps are patterns (have direction, no startingFoot)
        result.forEach { step ->
            assertNotNull(step.direction)
        }
    }

    @Test
    fun `returns step patterns with correct structure`() = runTest {
        // Given
        val repository = StepRepositoryAndroidImpl()
        
        // When
        val result = repository.getAllSteps().first()
        
        // Then
        assertTrue(result.isNotEmpty())
        // Verify all steps are patterns (foot-agnostic)
        result.forEach { step ->
            // Step should only have direction
            assertNotNull(step.direction)
            // Verify endingFoot method works
            val endingFromLeft = step.endingFoot(WeightFoot.LEFT)
            val endingFromRight = step.endingFoot(WeightFoot.RIGHT)
            assertEquals(WeightFoot.RIGHT, endingFromLeft)
            assertEquals(WeightFoot.LEFT, endingFromRight)
        }
    }
}
