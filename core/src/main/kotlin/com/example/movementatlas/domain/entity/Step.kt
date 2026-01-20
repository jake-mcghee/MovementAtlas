package com.example.movementatlas.domain.entity

/**
 * Represents a pattern for a single atomic weight transfer.
 * Foot-agnostic pattern that can be applied starting from either foot.
 * Always transfers weight to the opposite foot.
 */
sealed class Step {
    abstract val direction: Direction
    
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
    
    object InPlace : Step() {
        override val direction = Direction.IN_PLACE
    }
    
    object Forward : Step() {
        override val direction = Direction.FORWARD
    }
    
    object Backward : Step() {
        override val direction = Direction.BACKWARD
    }
    
    object Left : Step() {
        override val direction = Direction.LEFT
    }
    
    object Right : Step() {
        override val direction = Direction.RIGHT
    }
    
    companion object {
        /**
         * Creates a Step from a Direction enum value.
         */
        fun from(direction: Direction): Step = when (direction) {
            Direction.IN_PLACE -> InPlace
            Direction.FORWARD -> Forward
            Direction.BACKWARD -> Backward
            Direction.LEFT -> Left
            Direction.RIGHT -> Right
        }
    }
}
