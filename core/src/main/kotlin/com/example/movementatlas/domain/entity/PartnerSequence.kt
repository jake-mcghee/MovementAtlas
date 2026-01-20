package com.example.movementatlas.domain.entity

/**
 * Represents a partner dance sequence containing sequences for both dancers.
 * A PartnerSequence contains two SoloSequences: one for the LEAD and one for the FOLLOW.
 * The role is implicit in the field names (leadSequence vs followSequence).
 * SoloSequences themselves are role-agnostic and can be reused.
 * 
 * @param leadSequence The sequence for the LEAD dancer.
 * @param followSequence The sequence for the FOLLOW dancer.
 * @param id Identifier for the sequence. Null for non-persisted sequences.
 *          For user-saved sequences, assigned by repository when saved.
 *          For common and generated sequences, null (not persisted).
 * @param title Optional human-readable name for the partner sequence.
 */
data class PartnerSequence(
    val leadSequence: SoloSequence,
    val followSequence: SoloSequence,
    val id: Long? = null,
    val title: String? = null
)
