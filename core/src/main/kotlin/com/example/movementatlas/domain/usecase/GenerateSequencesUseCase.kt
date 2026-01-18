package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.Sequence
import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.repository.StepUnitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.first

class GenerateSequencesUseCase(
    private val stepUnitRepository: StepUnitRepository
) {
    operator fun invoke(startState: State, maxLength: Int = 5): Flow<List<Sequence>> = flow {
        val allStepUnits = stepUnitRepository.getAllStepUnits().first()
        val sequences = mutableListOf<Sequence>()
        
        fun generate(currentState: State, currentSequence: List<StepUnit>, depth: Int) {
            val compatibleStepUnits = allStepUnits.filter { stepUnit ->
                currentState.canTransitionTo(stepUnit)
            }
            
            // If no compatible step units or we've reached max length, add current sequence and stop
            if (compatibleStepUnits.isEmpty() || depth >= maxLength) {
                if (currentSequence.isNotEmpty()) {
                    sequences.add(Sequence(currentSequence, startState, currentState))
                }
                return
            }
            
            // Generate sequences for each compatible step unit
            compatibleStepUnits.forEach { stepUnit ->
                val nextState = currentState.applyTransition(stepUnit)
                val newSequence = currentSequence + stepUnit
                // Add sequence at each step unit
                sequences.add(Sequence(newSequence, startState, nextState))
                // Continue generating from next state
                generate(nextState, newSequence, depth + 1)
            }
        }
        
        generate(startState, emptyList(), 0)
        emit(sequences)
    }
}
