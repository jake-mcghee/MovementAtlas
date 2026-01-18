package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot

class GetStepUnitEntriesUseCase {
    operator fun invoke(stepUnit: StepUnit): List<WeightFoot> {
        // StepUnits are foot-agnostic and can start from either foot
        return listOf(WeightFoot.LEFT, WeightFoot.RIGHT)
    }
}
