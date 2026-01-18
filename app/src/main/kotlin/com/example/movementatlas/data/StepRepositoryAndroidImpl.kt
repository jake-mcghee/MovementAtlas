package com.example.movementatlas.data

import com.example.movementatlas.domain.data.DefaultStepProvider
import com.example.movementatlas.domain.data.DefaultStepUnitProvider
import com.example.movementatlas.domain.entity.StepUnit
import com.example.movementatlas.domain.repository.StepUnitRepository
import kotlinx.coroutines.flow.flowOf

/**
 * Android implementation of StepUnitRepository.
 * Uses the default step unit definitions from the core module.
 *
 * This implementation can be extended to load step units from
 * a database, network, or other Android-specific sources.
 */
class StepUnitRepositoryAndroidImpl : StepUnitRepository {

    private val stepUnits: List<StepUnit> by lazy {
        // Get steps directly from DefaultStepProvider since we need them synchronously
        val steps = DefaultStepProvider.getDefaultSteps()
        DefaultStepUnitProvider.getDefaultStepUnits(steps)
    }

    override fun getAllStepUnits() = flowOf(stepUnits)
}
