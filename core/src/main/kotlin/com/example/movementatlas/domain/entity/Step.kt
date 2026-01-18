package com.example.movementatlas.domain.entity

/**
 * Represents a pattern for a single atomic weight transfer.
 * Foot-agnostic pattern that can be applied starting from either foot.
 * Always transfers weight to the opposite foot.
 */
data class Step(
    val direction: Direction
) {
    /**
     * Computes the ending foot when this step pattern is applied from the given starting foot.
     * The ending foot is always the opposite of the starting foot.
     */
    fun endingFoot(startingFoot: WeightFoot): WeightFoot {
        return when (startingFoot) {
            WeightFoot.LEFT -> WeightFoot.RIGHT
            WeightFoot.RIGHT -> WeightFoot.LEFT
        }
    }
}
