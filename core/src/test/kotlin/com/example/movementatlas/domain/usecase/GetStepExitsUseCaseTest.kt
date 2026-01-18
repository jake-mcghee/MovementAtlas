package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class GetStepUnitExitsUseCaseTest {

    @Test
    fun `returns resulting weight feet by applying step unit to all valid entry states`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - from LEFT: L->R, from RIGHT: R->L
        assertEquals(2, result.size)
        assertTrue(result.contains(WeightFoot.LEFT))
        assertTrue(result.contains(WeightFoot.RIGHT))
    }

    @Test
    fun `returns correct exit states for DistanceTwo pattern`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - from LEFT: L->R->L, from RIGHT: R->L->R
        assertEquals(2, result.size)
        assertTrue(result.contains(WeightFoot.LEFT))
        assertTrue(result.contains(WeightFoot.RIGHT))
    }

    @Test
    fun `returns correct exit states for DistanceThree pattern`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceThree(step1 = stepPattern, step2 = stepPattern, step3 = stepPattern)

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - from LEFT: L->R->L->R, from RIGHT: R->L->R->L
        assertEquals(2, result.size)
        assertTrue(result.contains(WeightFoot.LEFT))
        assertTrue(result.contains(WeightFoot.RIGHT))
    }

    @Test
    fun `returns unique exit states`() {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)

        val useCase = GetStepUnitExitsUseCase()

        // When
        val result = useCase(stepUnit)

        // Then - should not have duplicates
        assertEquals(result.toSet().size, result.size)
    }
}
