package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of atomic movement steps (single weight transfers).
 * This is domain data that can be used by any platform implementation.
 */
object DefaultStepProvider {

    fun getDefaultSteps(): List<Step> = listOf(
        // Solo atomic steps - single weight transfers
        Step(
            id = "step-lr",
            name = "Left to Right",
            tags = listOf("atomic", "solo"),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        ),
        Step(
            id = "step-rl",
            name = "Right to Left",
            tags = listOf("atomic", "solo"),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.RIGHT,
            weightFootTo = WeightFoot.LEFT
        ),
        // Partner atomic steps - weight transfers for both lead and follow
        Step(
            id = "partner-step-lr-rl",
            name = "Lead Left→Right, Follow Right→Left",
            tags = listOf("atomic", "partner"),
            type = StepType.PARTNER,
            leadFrom = WeightFoot.LEFT,
            leadTo = WeightFoot.RIGHT,
            followFrom = WeightFoot.RIGHT,
            followTo = WeightFoot.LEFT
        ),
        Step(
            id = "partner-step-rl-lr",
            name = "Lead Right→Left, Follow Left→Right",
            tags = listOf("atomic", "partner"),
            type = StepType.PARTNER,
            leadFrom = WeightFoot.RIGHT,
            leadTo = WeightFoot.LEFT,
            followFrom = WeightFoot.LEFT,
            followTo = WeightFoot.RIGHT
        )
    )
}
