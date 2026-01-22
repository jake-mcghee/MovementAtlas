package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class DefaultPartnerSequenceProviderTest {

    @Test
    fun `getCommonPartnerSequences returns list of partner sequences`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // Then
        assertTrue("Should return at least one partner sequence", partnerSequences.isNotEmpty())
        partnerSequences.forEach { partnerSequence ->
            assertTrue("Should have partner step units", partnerSequence.partnerStepUnits.isNotEmpty())
            assertTrue("Lead should have step units", partnerSequence.leadStepUnits.isNotEmpty())
            assertTrue("Follow should have step units", partnerSequence.followStepUnits.isNotEmpty())
        }
    }

    @Test
    fun `getCommonPartnerSequences includes sequences with step units`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // Then
        assertTrue("Should have partner sequences", partnerSequences.isNotEmpty())
        // Verify that partner sequences have valid step units
        partnerSequences.forEach { partnerSequence ->
            // Both lead and follow should have step units
            assertTrue("Lead should have step units", partnerSequence.leadStepUnits.isNotEmpty())
            assertTrue("Follow should have step units", partnerSequence.followStepUnits.isNotEmpty())
        }
    }

    @Test
    fun `partner sequences can compute end weight foot for lead and follow`() {
        // Given
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // When & Then
        partnerSequences.forEach { partnerSequence ->
            // Both lead and follow should be able to compute end weight foot using default starting feet
            val leadEndFoot = partnerSequence.computeLeadEndWeightFoot()
            val followEndFoot = partnerSequence.computeFollowEndWeightFoot()
            
            assertNotNull("Lead should compute end foot", leadEndFoot)
            assertNotNull("Follow should compute end foot", followEndFoot)
            
            // Also test with explicit initial weight feet
            val leadEndFootExplicit = partnerSequence.computeLeadEndWeightFoot(partnerSequence.leadInitialWeightFoot)
            val followEndFootExplicit = partnerSequence.computeFollowEndWeightFoot(partnerSequence.followInitialWeightFoot)
            
            assertEquals("Should get same result with explicit starting foot", leadEndFoot, leadEndFootExplicit)
            assertEquals("Should get same result with explicit starting foot", followEndFoot, followEndFootExplicit)
        }
    }

    @Test
    fun `partner sequences can have same step units for lead and follow`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // Then
        // It's valid for lead and follow to have the same step units (symmetric patterns)
        partnerSequences.forEach { partnerSequence ->
            // This is allowed - same step units for both roles
            assertTrue("Should have partner step units", partnerSequence.partnerStepUnits.isNotEmpty())
            assertTrue("Lead should have step units", partnerSequence.leadStepUnits.isNotEmpty())
            assertTrue("Follow should have step units", partnerSequence.followStepUnits.isNotEmpty())
        }
    }

    @Test
    fun `partner sequences have correct initial weight feet`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // Then
        // Standard convention: Lead's first step is LEFT (weight on RIGHT), Follow's first step is RIGHT (weight on LEFT)
        partnerSequences.forEach { partnerSequence ->
            assertEquals("Lead should have weight on RIGHT initially (first step is LEFT)", WeightFoot.RIGHT, partnerSequence.leadInitialWeightFoot)
            assertEquals("Follow should have weight on LEFT initially (first step is RIGHT)", WeightFoot.LEFT, partnerSequence.followInitialWeightFoot)
        }
    }
}
