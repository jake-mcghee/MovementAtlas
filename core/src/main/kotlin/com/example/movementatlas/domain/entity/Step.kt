package com.example.movementatlas.domain.entity

/**
 * Represents a single atomic weight transfer.
 * For SOLO steps: transfers weight from one foot to the other.
 * For PARTNER steps: transfers weight for both lead and follow.
 */
data class Step(
    val id: String,
    val name: String,
    val tags: List<String>,
    val type: StepType,
    // For SOLO: weightFootFrom -> weightFootTo
    val weightFootFrom: WeightFoot? = null,
    val weightFootTo: WeightFoot? = null,
    // For PARTNER: lead and follow weight transfers
    val leadFrom: WeightFoot? = null,
    val leadTo: WeightFoot? = null,
    val followFrom: WeightFoot? = null,
    val followTo: WeightFoot? = null
) {
    init {
        require(
            (type == StepType.SOLO && weightFootFrom != null && weightFootTo != null && leadFrom == null && leadTo == null && followFrom == null && followTo == null) ||
            (type == StepType.PARTNER && leadFrom != null && leadTo != null && followFrom != null && followTo != null && weightFootFrom == null && weightFootTo == null)
        ) {
            "Step must have valid weight transfers for its type"
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Step) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
