package com.example.movementatlas.domain.repository

import com.example.movementatlas.domain.entity.SoloSequence
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for persisting and retrieving SoloSequence entities.
 * Provides minimal operations for solo sequence persistence.
 */
interface SequenceRepository {
    /**
     * Saves a solo sequence to persistent storage.
     * 
     * @param sequence The solo sequence to save.
     * @return Result indicating success or failure of the save operation.
     */
    suspend fun save(sequence: SoloSequence): Result<Unit>
    
    /**
     * Retrieves all solo sequences from persistent storage.
     * 
     * @return Flow emitting a list of all solo sequences. The Flow will emit updates
     *         whenever the underlying data changes.
     */
    suspend fun getAll(): Flow<List<SoloSequence>>
}
