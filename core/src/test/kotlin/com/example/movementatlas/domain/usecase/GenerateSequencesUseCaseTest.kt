package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepUnitRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class GenerateSequencesUseCaseTest {

    @Test
    fun `generates sequences from start weight foot respecting maxLength`() = runTest {
        // Given
        val startWeightFoot = WeightFoot.LEFT
        val maxLength = 2

        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceOne(step = stepPattern)

        val allStepUnits = listOf(stepUnit1, stepUnit2)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(allStepUnits)
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startWeightFoot, maxLength).first()

        // Then
        assertTrue(result.isNotEmpty())
        result.forEach { sequence ->
            assertTrue(sequence.stepUnits.size <= maxLength)
            assertEquals(startWeightFoot, sequence.startWeightFoot)
        }
    }

    @Test
    fun `respects maxLength parameter`() = runTest {
        // Given
        val startWeightFoot = WeightFoot.LEFT
        val maxLength = 1

        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit))
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startWeightFoot, maxLength).first()

        // Then
        result.forEach { sequence ->
            assertTrue(sequence.stepUnits.size <= maxLength)
        }
    }

    @Test
    fun `generates sequences with correct state transitions`() = runTest {
        // Given
        val startWeightFoot = WeightFoot.LEFT
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit))
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startWeightFoot, 1).first()

        // Then
        assertTrue(result.isNotEmpty())
        result.forEach { sequence ->
            if (sequence.stepUnits.isNotEmpty()) {
                // Starting from LEFT, applying one step should end on RIGHT
                assertEquals(WeightFoot.RIGHT, sequence.endWeightFoot)
            }
        }
    }

    @Test
    fun `generates multiple sequences when multiple step units available`() = runTest {
        // Given
        val startWeightFoot = WeightFoot.LEFT
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit1 = StepUnit.DistanceOne(step = stepPattern)
        val stepUnit2 = StepUnit.DistanceTwo(step1 = stepPattern, step2 = stepPattern)
        val stepUnit3 = StepUnit.DistanceThree(step1 = stepPattern, step2 = stepPattern, step3 = stepPattern)

        val stepUnitRepository = mockk<StepUnitRepository> {
            every { getAllStepUnits() } returns flowOf(listOf(stepUnit1, stepUnit2, stepUnit3))
        }

        val useCase = GenerateSequencesUseCase(stepUnitRepository)

        // When
        val result = useCase(startWeightFoot, 1).first()

        // Then
        assertTrue(result.size >= 3) // Should have at least one sequence per step unit
    }
}
