package com.example.movementatlas.domain.entity

/**
 * Represents a sequence of StepUnits - a reusable movement pattern for a single dancer.
 * Role-agnostic: the same sequence can be used for LEAD or FOLLOW dancers.
 * Weight feet are computed from stepUnits rather than stored.
 * 
 * @param stepUnits List of StepUnits that form this sequence.
 * @param id Identifier for the sequence. Null for non-persisted sequences.
 *          For user-saved sequences, assigned by repository when saved.
 *          For common and generated sequences, null (not persisted).
 * @param title Optional human-readable name for the sequence.
 */
data class SoloSequence(
    val stepUnits: List<StepUnit>,
    val id: Long? = null,
    val title: String? = null
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
