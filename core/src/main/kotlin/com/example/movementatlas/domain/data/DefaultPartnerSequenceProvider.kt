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
        // Reference step units directly from DefaultStepUnitProvider (type-safe, no string lookups)
        val basicStepUnit = DefaultStepUnitProvider.basicStepInPlace()
        
        // Compose Linear Basic from halves (same pattern, different starting point)
        val forwardHalfStepUnit = DefaultStepUnitProvider.linearBasicForwardHalf()
        val backwardHalfStepUnit = DefaultStepUnitProvider.linearBasicBackwardHalf()
        
        // Follow: forward half + backward half
        val linearBasicForFollow = SoloSequence(
            stepUnits = listOf(forwardHalfStepUnit, backwardHalfStepUnit),
            title = "Linear Basic Forward"
        )
        
        // Lead: backward half + forward half (same pattern, different starting point)
        val linearBasicForLead = SoloSequence(
            stepUnits = listOf(backwardHalfStepUnit, forwardHalfStepUnit),
            title = "Linear Basic Backwards"
        )
        
        // Basic step sequence for both dancers
        val basicStep = SoloSequence(
            stepUnits = listOf(basicStepUnit),
            title = basicStepUnit.title
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
