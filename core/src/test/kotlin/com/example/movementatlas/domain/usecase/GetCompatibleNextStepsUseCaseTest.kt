package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepUnitRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GetCompatibleNextStepsUseCaseTest {

    @Test
    fun `returns all step units that can transition from given weight foot`() = runTest {
        // Given
        val currentWeightFoot = WeightFoot.LEFT

        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)
        val stepUnit3 = StepUnit.DistanceThree(step1 = stepPattern, step3 = stepPattern)

        val allStepUnits = listOf(stepUnit1, stepUnit2, stepUnit3)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(allStepUnits)
        }

        val useCase = GetCompatibleNextStepUnitsUseCase(stepUnitRepository)

        // When
        val result = useCase(currentWeightFoot)

        // Then - all patterns can be applied from any foot
        assertEquals(3, result.size)
        assertTrue(result.contains(stepUnit1))
        assertTrue(result.contains(stepUnit2))
        assertTrue(result.contains(stepUnit3))
    }

    @Test
    fun `returns all step units for any weight foot since patterns are foot-agnostic`() = runTest {
        // Given
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit))
        }

        val useCase = GetCompatibleNextStepUnitsUseCase(stepUnitRepository)

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
    fun `returns empty list when no step units available`() = runTest {
        // Given
        val currentWeightFoot = WeightFoot.LEFT

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(emptyList())
        }

        val useCase = GetCompatibleNextStepUnitsUseCase(stepUnitRepository)

        // When
        val result = useCase(currentWeightFoot)

        // Then
        assertTrue(result.isEmpty())
    }
}
