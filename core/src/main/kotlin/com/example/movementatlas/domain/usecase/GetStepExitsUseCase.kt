package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot

class GetStepUnitExitsUseCase {
    operator fun invoke(stepUnit: StepUnit): List<WeightFoot> {
        // StepUnits always end on the opposite foot from where they started
        // Since they can start from either foot, they can end on either foot
        return listOf(WeightFoot.LEFT, WeightFoot.RIGHT)
    }
}
