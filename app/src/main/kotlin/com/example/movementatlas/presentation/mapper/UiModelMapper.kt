package com.example.movementatlas.presentation.mapper

import com.example.movementatlas.domain.entity.Sequence
import com.example.movementatlas.domain.entity.SoloState
import com.example.movementatlas.domain.entity.State
import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot
import com.example.movementatlas.presentation.model.SequenceUiModel
import com.example.movementatlas.presentation.model.StartStateOption
import com.example.movementatlas.presentation.model.StepUiModel
import com.example.movementatlas.presentation.model.StepUnitUiModel

/**
 * Mappers for converting domain entities to UI models.
 */
object UiModelMapper {

    fun Step.toUiModel(): StepUiModel = StepUiModel(
        id = id,
        name = name,
        tags = tags,
        difficulty = determineDifficulty(tags)
    )

    fun StepUnit.toUiModel(): StepUnitUiModel = StepUnitUiModel(
        id = id,
        name = name,
        tags = tags,
        difficulty = determineDifficulty(tags),
        stepCount = steps.size
    )

    fun Sequence.toUiModel(): SequenceUiModel = SequenceUiModel(
        stepUnits = stepUnits.map { it.toUiModel() },
        startStateDisplay = startState.toDisplayString(),
        endStateDisplay = endState.toDisplayString(),
        stepUnitCount = stepUnits.size
    )

    fun List<Sequence>.toUiModels(): List<SequenceUiModel> = map { it.toUiModel() }

    fun State.toDisplayString(): String = when (this) {
        is State.Solo -> "Weight on ${soloState.weightFoot.name}"
        is State.Partner -> "Lead: ${partnerState.lead.weightFoot.name}, Follow: ${partnerState.follow.weightFoot.name}"
    }

    fun SoloState.toDisplayString(): String = "Weight on ${weightFoot.name}"

    fun StartStateOption.toDomainState(): SoloState = when (this) {
        StartStateOption.LEFT -> SoloState(WeightFoot.LEFT)
        StartStateOption.RIGHT -> SoloState(WeightFoot.RIGHT)
    }

    fun SoloState.toStartStateOption(): StartStateOption = when (weightFoot) {
        WeightFoot.LEFT -> StartStateOption.LEFT
        WeightFoot.RIGHT -> StartStateOption.RIGHT
    }

    private fun determineDifficulty(tags: List<String>): String = when {
        "advanced" in tags -> "Advanced"
        "intermediate" in tags -> "Intermediate"
        "beginner" in tags -> "Beginner"
        else -> "Unknown"
    }
}
