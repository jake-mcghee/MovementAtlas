package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot

class GetStepUnitExitsUseCase {
    operator fun invoke(stepUnit: StepUnit): List<WeightFoot> {
        return stepUnit.preconditions()
            .filter { weightFoot -> stepUnit.canTransitionFrom(weightFoot) }
            .map { weightFoot -> stepUnit.computePostState(weightFoot) }
    }
}
