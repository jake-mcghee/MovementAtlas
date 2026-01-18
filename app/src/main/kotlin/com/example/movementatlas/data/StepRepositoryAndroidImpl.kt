package com.example.movementatlas.data

import com.example.movementatlas.domain.data.DefaultStepProvider
import com.example.movementatlas.domain.entity.Step
import com.example.movementatlas.domain.repository.StepRepository
import kotlinx.coroutines.flow.flowOf

/**
 * Android implementation of StepRepository.
 * Uses the default step definitions from the core module.
 *
 * This implementation can be extended to load steps from
 * a database, network, or other Android-specific sources.
 */
class StepRepositoryAndroidImpl : StepRepository {

    private val steps: List<Step> = DefaultStepProvider.getDefaultSteps()

    override fun getAllSteps() = flowOf(steps)

    override suspend fun getStepById(id: String): Step? {
        return steps.find { it.id == id }
    }
}
