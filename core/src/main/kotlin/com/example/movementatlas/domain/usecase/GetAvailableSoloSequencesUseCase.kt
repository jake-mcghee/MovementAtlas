package com.example.movementatlas.domain.usecase

import com.example.movementatlas.domain.data.DefaultSoloSequenceProvider
import com.example.movementatlas.domain.entity.SoloSequence
import com.example.movementatlas.domain.repository.SequenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf

/**
 * Use case that combines common solo sequences (from DefaultSoloSequenceProvider)
 * with user-saved solo sequences (from SequenceRepository).
 * 
 * Returns a Flow that emits all available solo sequences, combining both sources.
 * Future: Can be extended to accept a ratio parameter (0-10) to filter/weight sequences.
 */
class GetAvailableSoloSequencesUseCase(
    private val sequenceRepository: SequenceRepository
) {
    /**
     * Returns a Flow that emits all available solo sequences.
     * Combines common sequences (hardcoded) with user-saved sequences (from repository).
     * 
     * @return Flow emitting a list of all available SoloSequence objects.
     *         The Flow will emit updates whenever user-saved sequences change.
     */
    suspend fun invoke(): Flow<List<SoloSequence>> {
        val commonSequences = DefaultSoloSequenceProvider.getCommonSequences()
        val userSequencesFlow = sequenceRepository.getAll()
        
        // Combine common sequences (static) with user sequences (dynamic Flow)
        return combine(
            flowOf(commonSequences),
            userSequencesFlow
        ) { common, user ->
            common + user
        }
    }
}
