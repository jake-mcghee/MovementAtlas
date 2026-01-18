package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of StepUnits (groupings of 1-3 steps).
 * This is domain data that can be used by any platform implementation.
 */
object DefaultStepUnitProvider {

    fun getDefaultStepUnits(steps: List<Step>): List<StepUnit> {
        // Find atomic steps by ID
        val stepLR = steps.find { it.id == "step-lr" } ?: throw IllegalStateException("step-lr not found")
        val stepRL = steps.find { it.id == "step-rl" } ?: throw IllegalStateException("step-rl not found")
        val partnerStepLRRL = steps.find { it.id == "partner-step-lr-rl" } ?: throw IllegalStateException("partner-step-lr-rl not found")
        val partnerStepRLLR = steps.find { it.id == "partner-step-rl-lr" } ?: throw IllegalStateException("partner-step-rl-lr not found")

        return listOf(
            // Solo StepUnits
            // Single step units
            StepUnit(
                id = "unit-solo-single-lr",
                name = "Single Step Left to Right",
                tags = listOf("beginner", "solo", "single"),
                steps = listOf(stepLR),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
                postState = State.Solo(SoloState(WeightFoot.RIGHT)),
                type = StepType.SOLO
            ),
            StepUnit(
                id = "unit-solo-single-rl",
                name = "Single Step Right to Left",
                tags = listOf("beginner", "solo", "single"),
                steps = listOf(stepRL),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
                postState = State.Solo(SoloState(WeightFoot.LEFT)),
                type = StepType.SOLO
            ),
            // Double step units
            StepUnit(
                id = "unit-solo-double-lr",
                name = "Double Step Left-Right",
                tags = listOf("beginner", "solo", "double"),
                steps = listOf(stepLR, stepRL),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
                postState = State.Solo(SoloState(WeightFoot.LEFT)),
                type = StepType.SOLO
            ),
            StepUnit(
                id = "unit-solo-double-rl",
                name = "Double Step Right-Left",
                tags = listOf("beginner", "solo", "double"),
                steps = listOf(stepRL, stepLR),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
                postState = State.Solo(SoloState(WeightFoot.RIGHT)),
                type = StepType.SOLO
            ),
            // Triple step units (LRL pattern)
            StepUnit(
                id = "unit-solo-triple-lrl",
                name = "Basic Step LRL",
                tags = listOf("beginner", "solo", "basic", "triple"),
                steps = listOf(stepLR, stepRL, stepLR),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
                postState = State.Solo(SoloState(WeightFoot.RIGHT)),
                type = StepType.SOLO
            ),
            // Triple step units (RLR pattern)
            StepUnit(
                id = "unit-solo-triple-rlr",
                name = "Basic Step RLR",
                tags = listOf("beginner", "solo", "basic", "triple"),
                steps = listOf(stepRL, stepLR, stepRL),
                preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
                postState = State.Solo(SoloState(WeightFoot.LEFT)),
                type = StepType.SOLO
            ),
            // Partner StepUnits
            // Single step unit
            StepUnit(
                id = "unit-partner-single-lr-rl",
                name = "Partner Single Step",
                tags = listOf("beginner", "partner", "single"),
                steps = listOf(partnerStepLRRL),
                preconditions = listOf(
                    State.Partner(
                        PartnerState(
                            lead = SoloState(WeightFoot.LEFT),
                            follow = SoloState(WeightFoot.RIGHT)
                        )
                    )
                ),
                postState = State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.RIGHT),
                        follow = SoloState(WeightFoot.LEFT)
                    )
                ),
                type = StepType.PARTNER
            ),
            StepUnit(
                id = "unit-partner-single-rl-lr",
                name = "Partner Single Step Reverse",
                tags = listOf("beginner", "partner", "single"),
                steps = listOf(partnerStepRLLR),
                preconditions = listOf(
                    State.Partner(
                        PartnerState(
                            lead = SoloState(WeightFoot.RIGHT),
                            follow = SoloState(WeightFoot.LEFT)
                        )
                    )
                ),
                postState = State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.LEFT),
                        follow = SoloState(WeightFoot.RIGHT)
                    )
                ),
                type = StepType.PARTNER
            ),
            // Double step unit
            StepUnit(
                id = "unit-partner-double",
                name = "Partner Double Step",
                tags = listOf("beginner", "partner", "double"),
                steps = listOf(partnerStepLRRL, partnerStepRLLR),
                preconditions = listOf(
                    State.Partner(
                        PartnerState(
                            lead = SoloState(WeightFoot.LEFT),
                            follow = SoloState(WeightFoot.RIGHT)
                        )
                    )
                ),
                postState = State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.LEFT),
                        follow = SoloState(WeightFoot.RIGHT)
                    )
                ),
                type = StepType.PARTNER
            ),
            // Triple step unit (LRL pattern for partner)
            StepUnit(
                id = "unit-partner-triple-lrl",
                name = "Partner Basic Step LRL",
                tags = listOf("beginner", "partner", "basic", "triple"),
                steps = listOf(partnerStepLRRL, partnerStepRLLR, partnerStepLRRL),
                preconditions = listOf(
                    State.Partner(
                        PartnerState(
                            lead = SoloState(WeightFoot.LEFT),
                            follow = SoloState(WeightFoot.RIGHT)
                        )
                    )
                ),
                postState = State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.RIGHT),
                        follow = SoloState(WeightFoot.LEFT)
                    )
                ),
                type = StepType.PARTNER
            ),
            // Triple step unit (RLR pattern for partner)
            StepUnit(
                id = "unit-partner-triple-rlr",
                name = "Partner Basic Step RLR",
                tags = listOf("beginner", "partner", "basic", "triple"),
                steps = listOf(partnerStepRLLR, partnerStepLRRL, partnerStepRLLR),
                preconditions = listOf(
                    State.Partner(
                        PartnerState(
                            lead = SoloState(WeightFoot.RIGHT),
                            follow = SoloState(WeightFoot.LEFT)
                        )
                    )
                ),
                postState = State.Partner(
                    PartnerState(
                        lead = SoloState(WeightFoot.LEFT),
                        follow = SoloState(WeightFoot.RIGHT)
                    )
                ),
                type = StepType.PARTNER
            )
        )
    }
}
