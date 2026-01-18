package com.example.movementatlas.domain.entity

/**
 * Represents a sequence of StepUnits - a reusable movement pattern for a single dancer.
 * Role-agnostic: the same sequence can be used for LEAD or FOLLOW dancers.
 * Weight feet are computed from stepUnits rather than stored.
 * 
 * @param id Optional identifier for persistence. Null for generated sequences.
 * @param stepUnits List of StepUnits that form this sequence.
 */
data class SoloSequence(
    val id: String? = null,
    val stepUnits: List<StepUnit>
) {
    /**
     * Computes the ending weight foot after applying this sequence
     * starting from the given initial weight foot.
     * 
     * @param startWeightFoot The weight foot at the start of the sequence.
     * @return The weight foot at the end of the sequence.
     */
    fun computeEndWeightFoot(startWeightFoot: WeightFoot): WeightFoot {
        return stepUnits.fold(startWeightFoot) { currentFoot, stepUnit ->
            stepUnit.computePostState(currentFoot)
        }
    }
}
