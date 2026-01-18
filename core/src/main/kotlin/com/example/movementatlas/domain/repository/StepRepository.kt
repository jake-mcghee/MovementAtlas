package com.example.movementatlas.domain.repository

import com.example.movementatlas.domain.entity.Step
import kotlinx.coroutines.flow.Flow

interface StepRepository {
    fun getAllSteps(): Flow<List<Step>>
    suspend fun getStepById(id: String): Step?
}
