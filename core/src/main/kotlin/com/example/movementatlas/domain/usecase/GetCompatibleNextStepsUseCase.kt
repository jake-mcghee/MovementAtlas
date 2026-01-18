package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.entity.WeightFoot
import com.example.movementatlas.domain.repository.StepUnitRepository
import kotlinx.coroutines.flow.first

class GetCompatibleNextStepUnitsUseCase(
    private val stepUnitRepository: StepUnitRepository
) {
    suspend operator fun invoke(fromWeightFoot: WeightFoot): List<StepUnit> {
        val allStepUnits = stepUnitRepository.getAllStepUnits().first()
        return allStepUnits
    }
}
