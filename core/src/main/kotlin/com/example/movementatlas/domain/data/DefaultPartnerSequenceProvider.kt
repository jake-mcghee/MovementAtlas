package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of common partner sequences (hardcoded curated sequences).
 * These are domain data that can be used by any platform implementation.
 * 
 * Common partner sequences are separate from user-saved sequences (which are stored via PartnerSequenceRepository).
 * 
 * To add a new partner sequence:
 * 1. Create a list of PartnerStepUnit objects that define what both dancers do at each step
 * 2. Use PartnerSequence.fromPartnerStepUnits() to build the PartnerSequence
 * 3. Add it to the [allPartnerSequences] list
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
        return allPartnerSequences
    }

    // ============================================================================
    // Partner Sequences List
    // Add new partner sequences to this list
    // ============================================================================

    private val allPartnerSequences: List<PartnerSequence> = listOf(
        basicPartnerPattern(),
        linearBasicPartnerPattern()
    )

    // ============================================================================
    // Partner Sequence Functions
    // Define partner sequences using PartnerStepUnit lists
    // ============================================================================

    /**
     * Basic partner pattern: both dancers do basic step in place
     * Lead's first step is LEFT (weight on RIGHT), Follow's first step is RIGHT (weight on LEFT)
     */
    private fun basicPartnerPattern(): PartnerSequence {
        val basicStepUnit = DefaultStepUnitProvider.basicStepInPlace()
        
        return PartnerSequence.fromPartnerStepUnits(
            partnerStepUnits = listOf(
                PartnerStepUnit(
                    leadStepUnit = basicStepUnit,
                    followStepUnit = basicStepUnit
                )
            ),
            title = "Basic Partner Pattern"
        )
    }

    /**
     * Linear Basic pattern: lead does backwards then forward, follow does forward then backwards
     * Lead's first step is LEFT (weight on RIGHT), Follow's first step is RIGHT (weight on LEFT)
     */
    private fun linearBasicPartnerPattern(): PartnerSequence {
        val forwardHalfStepUnit = DefaultStepUnitProvider.linearBasicForwardHalf()
        val backwardHalfStepUnit = DefaultStepUnitProvider.linearBasicBackwardHalf()
        
        return PartnerSequence.fromPartnerStepUnits(
            partnerStepUnits = listOf(
                // Step 1: Lead does backward half, Follow does forward half
                PartnerStepUnit(
                    leadStepUnit = backwardHalfStepUnit,
                    followStepUnit = forwardHalfStepUnit
                ),
                // Step 2: Lead does forward half, Follow does backward half
                PartnerStepUnit(
                    leadStepUnit = forwardHalfStepUnit,
                    followStepUnit = backwardHalfStepUnit
                )
            ),
            title = "Linear Basic Partner Pattern"
        )
    }
}
