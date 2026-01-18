package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetCompatibleNextStepsUseCaseTest {

    @Test
    fun `returns all step units that can transition from given weight foot`() {
        // Given
        val currentWeightFoot = WeightFoot.LEFT

        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)
        val stepUnit3 = StepUnit.DistanceThree(step1 = stepPattern, step3 = stepPattern)

        val allStepUnits = listOf(stepUnit1, stepUnit2, stepUnit3)

        val useCase = GetCompatibleNextStepUnitsUseCase(allStepUnits)

        // When
        val result = useCase(currentWeightFoot)

        // Then - all patterns can be applied from any foot
        assertEquals(3, result.size)
        assertTrue(result.contains(stepUnit1))
        assertTrue(result.contains(stepUnit2))
        assertTrue(result.contains(stepUnit3))
    }

    @Test
    fun `returns all step units for any weight foot since patterns are foot-agnostic`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val useCase = GetCompatibleNextStepUnitsUseCase(listOf(stepUnit))

        // When
        val resultFromLeft = useCase(WeightFoot.LEFT)
        val resultFromRight = useCase(WeightFoot.RIGHT)

        // Then - patterns work from either foot
        assertEquals(1, resultFromLeft.size)
        assertEquals(1, resultFromRight.size)
        assertTrue(resultFromLeft.contains(stepUnit))
        assertTrue(resultFromRight.contains(stepUnit))
    }

    @Test
    fun `returns empty list when no step units available`() {
        // Given
        val currentWeightFoot = WeightFoot.LEFT

        val useCase = GetCompatibleNextStepUnitsUseCase(emptyList())

        // When
        val result = useCase(currentWeightFoot)

        // Then
        assertTrue(result.isEmpty())
    }
}
