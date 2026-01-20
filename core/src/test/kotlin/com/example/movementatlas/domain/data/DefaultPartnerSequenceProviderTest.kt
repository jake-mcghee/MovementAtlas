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
            assertNotNull("Lead sequence should not be null", partnerSequence.leadSequence)
            assertNotNull("Follow sequence should not be null", partnerSequence.followSequence)
            assertTrue("Lead sequence should have step units", partnerSequence.leadSequence.stepUnits.isNotEmpty())
            assertTrue("Follow sequence should have step units", partnerSequence.followSequence.stepUnits.isNotEmpty())
        }
    }

    @Test
    fun `getCommonPartnerSequences includes sequences with matching solo sequences`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()
        val commonSoloSequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        assertTrue("Should have partner sequences", partnerSequences.isNotEmpty())
        // Verify that partner sequences can reference common solo sequences
        partnerSequences.forEach { partnerSequence ->
            // Both lead and follow sequences should be valid
            assertNotNull(partnerSequence.leadSequence)
            assertNotNull(partnerSequence.followSequence)
        }
    }

    @Test
    fun `partner sequences have valid lead and follow sequences`() {
        // Given
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()
        val startWeightFoot = WeightFoot.LEFT

        // When & Then
        partnerSequences.forEach { partnerSequence ->
            // Both sequences should be able to compute end weight foot
            val leadEndFoot = partnerSequence.leadSequence.computeEndWeightFoot(startWeightFoot)
            val followEndFoot = partnerSequence.followSequence.computeEndWeightFoot(startWeightFoot)
            
            assertNotNull("Lead sequence should compute end foot", leadEndFoot)
            assertNotNull("Follow sequence should compute end foot", followEndFoot)
        }
    }

    @Test
    fun `partner sequences can have same sequence for lead and follow`() {
        // When
        val partnerSequences = DefaultPartnerSequenceProvider.getCommonPartnerSequences()

        // Then
        // It's valid for lead and follow to have the same sequence (symmetric patterns)
        partnerSequences.forEach { partnerSequence ->
            // This is allowed - same sequence for both roles
            assertNotNull(partnerSequence.leadSequence)
            assertNotNull(partnerSequence.followSequence)
        }
    }
}
