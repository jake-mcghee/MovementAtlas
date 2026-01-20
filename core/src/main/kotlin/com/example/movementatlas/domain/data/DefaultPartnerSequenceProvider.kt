package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of common partner sequences (hardcoded curated sequences).
 * These are domain data that can be used by any platform implementation.
 * 
 * Common partner sequences are separate from user-saved sequences (which are stored via PartnerSequenceRepository).
 */
object DefaultPartnerSequenceProvider {

    /**
     * Returns a list of common partner sequences.
     * Each sequence pairs a lead sequence with a follow sequence.
     * Common sequences have null IDs since they are not persisted.
     * 
     * @return List of common PartnerSequence objects with titles but null IDs.
     */
    fun getCommonPartnerSequences(): List<PartnerSequence> {
        // Reference solo sequences directly via functions (type-safe, no string lookups)
        val basicStep = DefaultSoloSequenceProvider.basicStepInPlace()
        
        // Compose Linear Basic from halves (same pattern, different starting point)
        val forwardHalf = DefaultSoloSequenceProvider.linearBasicForwardHalf()
        val backwardHalf = DefaultSoloSequenceProvider.linearBasicBackwardHalf()
        
        // Follow: forward half + backward half
        val linearBasicForFollow = SoloSequence(
            stepUnits = forwardHalf.stepUnits + backwardHalf.stepUnits,
            title = "Linear Basic Forward"
        )
        
        // Lead: backward half + forward half (same pattern, different starting point)
        val linearBasicForLead = SoloSequence(
            stepUnits = backwardHalf.stepUnits + forwardHalf.stepUnits,
            title = "Linear Basic Backwards"
        )
        
        return listOf(
            // Basic partner pattern: both dancers do basic step
            PartnerSequence(
                leadSequence = basicStep,
                followSequence = basicStep,
                title = "Basic Partner Pattern"
            ),
            // Linear Basic pattern: lead does backwards variant, follow does forward variant
            PartnerSequence(
                leadSequence = linearBasicForLead,
                followSequence = linearBasicForFollow,
                title = "Linear Basic Partner Pattern"
            )
        )
    }
}
