package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepUnitEntriesUseCaseTest {

    @Test
    fun `returns all weight feet that can transition from step unit`() {
        // Given
        val stepPattern = Step.InPlace
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val useCase = GetStepUnitEntriesUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - patterns can be applied from either foot
        assertEquals(2, result.size)
        assertTrue(result.contains(WeightFoot.LEFT))
        assertTrue(result.contains(WeightFoot.RIGHT))
    }

    @Test
    fun `returns both feet for any step unit pattern`() {
        // Given
        val stepPattern = Step.InPlace
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern, step3 = stepPattern)
        val stepUnit3 = StepUnit.DistanceThree(step1 = stepPattern, step3 = stepPattern)

        val useCase = GetStepUnitEntriesUseCase()

        // When
        val result1 = useCase(stepUnit1)
        val result2 = useCase(stepUnit2)
        val result3 = useCase(stepUnit3)

        // Then - all patterns work from both feet
        assertEquals(2, result1.size)
        assertEquals(2, result2.size)
        assertEquals(2, result3.size)
        assertTrue(result1.containsAll(listOf(WeightFoot.LEFT, WeightFoot.RIGHT)))
        assertTrue(result2.containsAll(listOf(WeightFoot.LEFT, WeightFoot.RIGHT)))
        assertTrue(result3.containsAll(listOf(WeightFoot.LEFT, WeightFoot.RIGHT)))
    }
}
