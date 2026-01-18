package com.example.movementatlas.domain.repository

import com.example.movementatlas.domain.entity.StepUnit
import kotlinx.coroutines.flow.Flow

interface StepUnitRepository {
    fun getAllStepUnits(): Flow<List<StepUnit>>
    suspend fun getStepUnitById(id: String): StepUnit?
}
