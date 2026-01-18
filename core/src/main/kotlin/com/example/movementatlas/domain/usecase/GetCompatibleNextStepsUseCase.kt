package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot

class GetCompatibleNextStepUnitsUseCase(
    private val allStepUnits: List<StepUnit>
) {
    operator fun invoke(fromWeightFoot: WeightFoot): List<StepUnit> {
        return allStepUnits
    }
}
