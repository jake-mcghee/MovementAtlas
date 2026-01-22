package com.example.movementatlas.domain.entity

/**
 * Represents a partner dance sequence containing step units for both dancers.
 * A PartnerSequence contains a list of PartnerStepUnit objects, each defining what both
 * the LEAD and FOLLOW dancers do at that step.
 * 
 * Step units are the fundamental building block - sequences are derived from step units.
 * 
 * Standard convention:
 * - Lead's first step is with LEFT foot (so weight starts on RIGHT)
 * - Follow's first step is with RIGHT foot (so weight starts on LEFT)
 * 
 * @param partnerStepUnits List of PartnerStepUnit objects defining what both dancers do at each step.
 * @param id Identifier for the sequence. Null for non-persisted sequences.
 *          For user-saved sequences, assigned by repository when saved.
 *          For common and generated sequences, null (not persisted).
 * @param title Optional human-readable name for the partner sequence.
 */
data class PartnerSequence(
    val partnerStepUnits: List<PartnerStepUnit>,
    val id: Long? = null,
    val title: String? = null
) {
    init {
        require(partnerStepUnits.isNotEmpty()) { "Partner step units list cannot be empty" }
    }

    /**
     * The initial weight foot for the LEAD dancer at the start of the sequence.
     * Always RIGHT, because lead's first step is with LEFT foot (opposite of weight).
     */
    val leadInitialWeightFoot: WeightFoot
        get() = WeightFoot.RIGHT

    /**
     * The initial weight foot for the FOLLOW dancer at the start of the sequence.
     * Always LEFT, because follow's first step is with RIGHT foot (opposite of weight).
     */
    val followInitialWeightFoot: WeightFoot
        get() = WeightFoot.LEFT

    /**
     * Computes the ending weight foot for the lead dancer after applying this sequence
     * starting from the sequence's default initial weight foot.
     * 
     * @return The weight foot at the end of the sequence for the lead dancer.
     */
    fun computeLeadEndWeightFoot(): WeightFoot {
        return computeLeadEndWeightFoot(leadInitialWeightFoot)
    }

    /**
     * Computes the ending weight foot for the lead dancer after applying this sequence
     * starting from the given initial weight foot.
     * 
     * @param startWeightFoot The weight foot at the start of the sequence.
     * @return The weight foot at the end of the sequence for the lead dancer.
     */
    fun computeLeadEndWeightFoot(startWeightFoot: WeightFoot): WeightFoot {
        return partnerStepUnits.fold(startWeightFoot) { currentFoot, partnerStepUnit ->
            partnerStepUnit.leadStepUnit.computePostState(currentFoot)
        }
    }

    /**
     * Computes the ending weight foot for the follow dancer after applying this sequence
     * starting from the sequence's default initial weight foot.
     * 
     * @return The weight foot at the end of the sequence for the follow dancer.
     */
    fun computeFollowEndWeightFoot(): WeightFoot {
        return computeFollowEndWeightFoot(followInitialWeightFoot)
    }

    /**
     * Computes the ending weight foot for the follow dancer after applying this sequence
     * starting from the given initial weight foot.
     * 
     * @param startWeightFoot The weight foot at the start of the sequence.
     * @return The weight foot at the end of the sequence for the follow dancer.
     */
    fun computeFollowEndWeightFoot(startWeightFoot: WeightFoot): WeightFoot {
        return partnerStepUnits.fold(startWeightFoot) { currentFoot, partnerStepUnit ->
            partnerStepUnit.followStepUnit.computePostState(currentFoot)
        }
    }

    /**
     * Helper property to get lead step units as a list.
     */
    val leadStepUnits: List<StepUnit>
        get() = partnerStepUnits.map { it.leadStepUnit }

    /**
     * Helper property to get follow step units as a list.
     */
    val followStepUnits: List<StepUnit>
        get() = partnerStepUnits.map { it.followStepUnit }

    companion object {
        /**
         * Builds a PartnerSequence from a list of PartnerStepUnit objects.
         * Standard convention: Lead's first step is LEFT (weight on RIGHT), Follow's first step is RIGHT (weight on LEFT).
         * 
         * @param partnerStepUnits List of PartnerStepUnit objects defining what both dancers do at each step.
         * @param title Optional title for the partner sequence. If null, will try to derive from partner step units.
         * @return A PartnerSequence built from the partner step units.
         */
        fun fromPartnerStepUnits(
            partnerStepUnits: List<PartnerStepUnit>,
            title: String? = null
        ): PartnerSequence {
            // Derive title from first partner step unit if not provided
            val derivedTitle = title ?: partnerStepUnits.firstOrNull()?.title
            
            return PartnerSequence(
                partnerStepUnits = partnerStepUnits,
                title = derivedTitle
            )
        }
    }
}
