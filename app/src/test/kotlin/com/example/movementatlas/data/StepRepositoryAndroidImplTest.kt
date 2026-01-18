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
    }

    @Test
    fun `returns step by ID`() = runTest {
        // Given
        val repository = StepRepositoryAndroidImpl()
        val allSteps = repository.getAllSteps().first()
        val expectedStep = allSteps.first()
        
        // When
        val result = repository.getStepById(expectedStep.id)
        
        // Then
        assertNotNull(result)
        assertEquals(expectedStep.id, result?.id)
        assertEquals(expectedStep.name, result?.name)
    }

    @Test
    fun `returns null for missing step ID`() = runTest {
        // Given
        val repository = StepRepositoryAndroidImpl()
        
        // When
        val result = repository.getStepById("non-existent-id")
        
        // Then
        assertNull(result)
    }
}
