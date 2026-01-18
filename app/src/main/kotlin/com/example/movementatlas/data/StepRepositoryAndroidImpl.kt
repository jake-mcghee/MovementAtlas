package com.example.movementatlas.data

import com.example.movementatlas.domain.entity.*
import com.example.movementatlas.domain.repository.StepRepository
import kotlinx.coroutines.flow.flowOf

class StepRepositoryAndroidImpl : StepRepository {

    private val steps = listOf(
        // Solo steps
        Step(
            id = "solo-basic-lr",
            name = "Basic Step Left to Right",
            tags = listOf("beginner", "solo", "basic"),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        ),
        Step(
            id = "solo-basic-rl",
            name = "Basic Step Right to Left",
            tags = listOf("beginner", "solo", "basic"),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        ),
        Step(
            id = "solo-turn",
            name = "Turn",
            tags = listOf("intermediate", "solo", "turn"),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        ),
        Step(
            id = "solo-pivot",
            name = "Pivot",
            tags = listOf("intermediate", "solo", "pivot"),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.RIGHT))),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        ),
        Step(
            id = "solo-step-forward",
            name = "Step Forward",
            tags = listOf("beginner", "solo"),
            preconditions = listOf(State.Solo(SoloState(WeightFoot.LEFT))),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.SOLO
        ),
        // Partner steps
        Step(
            id = "partner-lead-step",
            name = "Lead Step",
            tags = listOf("beginner", "partner", "lead"),
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
        Step(
            id = "partner-follow-step",
            name = "Follow Step",
            tags = listOf("beginner", "partner", "follow"),
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
        Step(
            id = "partner-turn",
            name = "Partner Turn",
            tags = listOf("intermediate", "partner", "turn"),
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
        )
    )

    override fun getAllSteps() = flowOf(steps)

    override suspend fun getStepById(id: String): Step? {
        return steps.find { it.id == id }
    }
}
