package com.example.movementatlas.domain.repository

import com.example.movementatlas.domain.entity.PartnerSequence
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for persisting and retrieving PartnerSequence entities.
 * Provides minimal operations for partner sequence persistence.
 */
interface PartnerSequenceRepository {
    /**
     * Saves a partner sequence to persistent storage.
     * 
     * @param partnerSequence The partner sequence to save.
     * @return Result indicating success or failure of the save operation.
     */
    suspend fun save(partnerSequence: PartnerSequence): Result<Unit>
    
    /**
     * Retrieves all partner sequences from persistent storage.
     * 
     * @return Flow emitting a list of all partner sequences. The Flow will emit updates
     *         whenever the underlying data changes.
     */
    suspend fun getAll(): Flow<List<PartnerSequence>>
}
