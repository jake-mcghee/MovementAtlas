package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class PartnerStateTest {

    @Test
    fun `PartnerState is created with lead and follow SoloStates`() {
        // Given
        val lead = SoloState(WeightFoot.LEFT)
        val follow = SoloState(WeightFoot.RIGHT)
        
        // When
        val partnerState = PartnerState(lead, follow)
        
        // Then
        assertEquals(lead, partnerState.lead)
        assertEquals(follow, partnerState.follow)
    }

    @Test
    fun `PartnerState equality is based on lead and follow`() {
        // Given
        val lead1 = SoloState(WeightFoot.LEFT)
        val follow1 = SoloState(WeightFoot.RIGHT)
        val partnerState1 = PartnerState(lead1, follow1)
        val partnerState2 = PartnerState(lead1, follow1)
        val partnerState3 = PartnerState(SoloState(WeightFoot.RIGHT), follow1)
        
        // Then
        assertEquals(partnerState1, partnerState2)
        assertNotEquals(partnerState1, partnerState3)
    }
}
