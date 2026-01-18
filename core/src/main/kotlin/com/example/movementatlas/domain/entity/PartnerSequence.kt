package com.example.movementatlas.domain.entity

/**
 * Represents a partner dance sequence containing sequences for both dancers.
 * A PartnerSequence contains two SoloSequences: one for the LEAD and one for the FOLLOW.
 * The role is implicit in the field names (leadSequence vs followSequence).
 * SoloSequences themselves are role-agnostic and can be reused.
 */
data class PartnerSequence(
    val leadSequence: SoloSequence,
    val followSequence: SoloSequence
)
