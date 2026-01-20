package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.SoloSequence
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GenerateSequencesUseCase(
    private val allStepUnits: List<StepUnit>
) {
    operator fun invoke(
        startWeightFoot: WeightFoot,
        maxLength: Int = 5
    ): Flow<List<SoloSequence>> = flow {
        val sequences = mutableListOf<SoloSequence>()
        
        fun generate(currentWeightFoot: WeightFoot, currentSequence: List<StepUnit>, depth: Int) {
            val compatibleStepUnits = allStepUnits
            
            // If no compatible step units or we've reached max length, add current sequence and stop
            if (compatibleStepUnits.isEmpty() || depth >= maxLength) {
                if (currentSequence.isNotEmpty()) {
                    sequences.add(SoloSequence(
                        stepUnits = currentSequence
                    ))
                }
                return
            }
            
            // Generate sequences for each compatible step unit
            compatibleStepUnits.forEach { stepUnit ->
                val nextWeightFoot = stepUnit.computePostState(currentWeightFoot)
                val newSequence = currentSequence + stepUnit
                // Add sequence at each step unit
                sequences.add(SoloSequence(
                    stepUnits = newSequence
                ))
                // Continue generating from next weight foot
                generate(nextWeightFoot, newSequence, depth + 1)
            }
        }
        
        generate(startWeightFoot, emptyList(), 0)
        emit(sequences)
    }
}
