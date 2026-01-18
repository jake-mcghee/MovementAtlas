package com.example.movementatlas.domain.entity

data class Step(
    val id: String,
    val name: String,
    val tags: List<String>,
    val preconditions: List<State>,
    val postState: State,
    val type: StepType
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Step) return false
        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
