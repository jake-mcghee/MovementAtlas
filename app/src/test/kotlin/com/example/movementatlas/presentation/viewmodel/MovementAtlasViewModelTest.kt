package com.example.movementatlas.presentation.viewmodel

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.usecase.GenerateSequencesUseCase
import com.example.movementatlas.domain.usecase.GetCompatibleNextStepUnitsUseCase
import com.example.movementatlas.presentation.model.StartStateOption
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MovementAtlasViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is correct`() = runTest {
        // Given
        val generateSequencesUseCase = mockk<GenerateSequencesUseCase>()
        val getCompatibleNextStepUnitsUseCase = mockk<GetCompatibleNextStepUnitsUseCase>()

        // When
        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepUnitsUseCase)
        val initialState = viewModel.uiState.value

        // Then
        assertNull(initialState.selectedStartState)
        assertTrue(initialState.sequences.isEmpty())
        assertFalse(initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun `generateSequences updates state with results`() = runTest(testDispatcher) {
        // Given
        val startStateOption = StartStateOption.LEFT
        val startWeightFoot = WeightFoot.LEFT
        val stepPattern = Step(direction = Direction.IN_PLACE)
        val stepUnit = StepUnit.DistanceOne(step = stepPattern)
        val sequence = SoloSequence(
            id = null,
            stepUnits = listOf(stepUnit)
        )

        val generateSequencesUseCase = mockk<GenerateSequencesUseCase>()
        coEvery { generateSequencesUseCase(startWeightFoot, any<Int>()) } returns flowOf(listOf(sequence))
        val getCompatibleNextStepUnitsUseCase = mockk<GetCompatibleNextStepUnitsUseCase>()

        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepUnitsUseCase)

        // When
        viewModel.generateSequences(startStateOption)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(startStateOption, state.selectedStartState)
        assertEquals(1, state.sequences.size)
        assertFalse(state.isLoading)
    }

    @Test
    fun `generateSequences handles errors`() = runTest(testDispatcher) {
        // Given
        val startStateOption = StartStateOption.LEFT
        val startWeightFoot = WeightFoot.LEFT
        val errorMessage = "Error generating sequences"

        val generateSequencesUseCase = mockk<GenerateSequencesUseCase>()
        coEvery { generateSequencesUseCase(startWeightFoot, any<Int>()) } throws Exception(errorMessage)
        val getCompatibleNextStepUnitsUseCase = mockk<GetCompatibleNextStepUnitsUseCase>()

        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepUnitsUseCase)

        // When
        viewModel.generateSequences(startStateOption)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertNotNull(state.error)
        assertTrue(state.error!!.contains(errorMessage))
        assertFalse(state.isLoading)
    }
}
