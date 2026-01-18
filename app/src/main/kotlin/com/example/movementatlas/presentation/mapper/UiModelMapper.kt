package com.example.movementatlas.presentation.mapper

import com.example.movementatlas.domain.entity.SoloSequence
import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot
import com.example.movementatlas.presentation.model.SequenceUiModel
import com.example.movementatlas.presentation.model.StartStateOption
import com.example.movementatlas.presentation.model.StepUnitUiModel

/**
 * Mappers for converting domain entities to UI models.
 */
object UiModelMapper {

    fun StepUnit.toUiModel(): StepUnitUiModel = StepUnitUiModel(
        id = hashCode().toString(), // Use hashCode as identifier for UI
        name = when (this) {
            is StepUnit.DistanceOne -> "Single Step"
            is StepUnit.DistanceTwo -> "Double Step"
            is StepUnit.DistanceThree -> "Triple Step"
        },
        tags = emptyList(),
        difficulty = "Unknown",
        stepCount = steps.size
    )

    fun SoloSequence.toUiModel(startWeightFoot: WeightFoot): SequenceUiModel {
        val endWeightFoot = computeEndWeightFoot(startWeightFoot)
        return SequenceUiModel(
            stepUnits = stepUnits.map { it.toUiModel() },
            startStateDisplay = "Weight on ${startWeightFoot.name}",
            endStateDisplay = "Weight on ${endWeightFoot.name}",
            stepUnitCount = stepUnits.size
        )
    }

    fun List<SoloSequence>.toUiModels(startWeightFoot: WeightFoot): List<SequenceUiModel> = 
        map { it.toUiModel(startWeightFoot) }

    fun WeightFoot.toDisplayString(): String = "Weight on ${name}"

    fun StartStateOption.toWeightFoot(): WeightFoot = when (this) {
        StartStateOption.LEFT -> WeightFoot.LEFT
        StartStateOption.RIGHT -> WeightFoot.RIGHT
    }

    fun WeightFoot.toStartStateOption(): StartStateOption = when (this) {
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
