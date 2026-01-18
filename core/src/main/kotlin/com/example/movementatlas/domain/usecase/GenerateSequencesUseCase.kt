package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.Sequence
import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.repository.StepRepository
import com.example.movementatlas.domain.repository.StateTransitionRules
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class GenerateSequencesUseCase(
    private val stepRepository: StepRepository,
    private val transitionRules: StateTransitionRules
) {
    operator fun invoke(startState: State, maxLength: Int = 5): Flow<List<Sequence>> = flow {
        val allSteps = stepRepository.getAllSteps().first()
        val sequences = mutableListOf<Sequence>()
        
        fun generate(currentState: State, currentSequence: List<com.example.movementatlas.domain.entity.Step>, depth: Int) {
            val compatibleSteps = allSteps.filter { step ->
                transitionRules.isValidTransition(currentState, step)
            }
            
            // If no compatible steps or we've reached max length, add current sequence and stop
            if (compatibleSteps.isEmpty() || depth >= maxLength) {
                if (currentSequence.isNotEmpty()) {
                    sequences.add(Sequence(currentSequence, startState, currentState))
                }
                return
            }
            
            // Generate sequences for each compatible step
            compatibleSteps.forEach { step ->
                val nextState = transitionRules.applyTransition(currentState, step)
                val newSequence = currentSequence + step
                // Add sequence at each step
                sequences.add(Sequence(newSequence, startState, nextState))
                // Continue generating from next state
                generate(nextState, newSequence, depth + 1)
            }
        }
        
        generate(startState, emptyList(), 0)
        emit(sequences)
    }
}
