package com.example.movementatlas.presentation.viewmodel

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.usecase.GenerateSequencesUseCase
import com.example.movementatlas.domain.usecase.GetCompatibleNextStepsUseCase
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
        val getCompatibleNextStepsUseCase = mockk<GetCompatibleNextStepsUseCase>()
        
        // When
        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepsUseCase)
        val initialState = viewModel.uiState.value
        
        // Then
        assertNull(initialState.selectedState)
        assertTrue(initialState.sequences.isEmpty())
        assertFalse(initialState.isLoading)
        assertNull(initialState.error)
    }

    @Test
    fun `generateSequences updates state with results`() = runTest(testDispatcher) {
        // Given
        val startState = SoloState(WeightFoot.LEFT)
        val sequence = Sequence(
            steps = listOf(
                Step(
                    id = "step-1",
                    name = "Step 1",
                    tags = emptyList(),
                    preconditions = listOf(State.Solo(startState)),
                    postState = State.Solo(SoloState(WeightFoot.RIGHT)),
                    type = StepType.SOLO
                )
            ),
            startState = State.Solo(startState),
            endState = State.Solo(SoloState(WeightFoot.RIGHT))
        )

        val generateSequencesUseCase = mockk<GenerateSequencesUseCase>()
        coEvery { generateSequencesUseCase(State.Solo(startState), any<Int>()) } returns flowOf(listOf(sequence))
        val getCompatibleNextStepsUseCase = mockk<GetCompatibleNextStepsUseCase>()

        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepsUseCase)

        // When
        viewModel.generateSequences(startState)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertEquals(startState, state.selectedState)
        assertEquals(1, state.sequences.size)
        assertEquals(sequence, state.sequences.first())
        assertFalse(state.isLoading)
    }

    @Test
    fun `generateSequences handles errors`() = runTest(testDispatcher) {
        // Given
        val startState = SoloState(WeightFoot.LEFT)
        val errorMessage = "Error generating sequences"

        val generateSequencesUseCase = mockk<GenerateSequencesUseCase>()
        coEvery { generateSequencesUseCase(State.Solo(startState), any<Int>()) } throws Exception(errorMessage)
        val getCompatibleNextStepsUseCase = mockk<GetCompatibleNextStepsUseCase>()

        val viewModel = MovementAtlasViewModel(generateSequencesUseCase, getCompatibleNextStepsUseCase)

        // When
        viewModel.generateSequences(startState)
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val state = viewModel.uiState.value
        assertNotNull(state.error)
        assertTrue(state.error!!.contains(errorMessage))
        assertFalse(state.isLoading)
    }
}
