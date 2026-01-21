package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*

/**
 * Provides the default set of common solo sequences (hardcoded curated sequences).
 * These are domain data that can be used by any platform implementation.
 * 
 * Common sequences are separate from user-saved sequences (which are stored via SequenceRepository).
 * Common sequences have null IDs since they are not persisted.
 * 
 * To add a new common sequence:
 * 1. Create a function (e.g., `myNewSequence()`) using the `commonSequence` helper
 * 2. Add it to the [allSequences] list
 */
object DefaultSoloSequenceProvider {



    /**
     * Returns a list of common solo sequences.
     * Each sequence represents a well-known movement pattern that can be reused.
     * Common sequences have null IDs since they are not persisted.
     * 
     * Sequences are built from step units in DefaultStepUnitProvider.
     * 
     * @return List of common SoloSequence objects with titles but null IDs.
     */
    fun getCommonSequences(): List<SoloSequence> {
        return DefaultStepUnitProvider.getCommonStepUnits().map { stepUnit ->
            SoloSequence(
                stepUnits = listOf(stepUnit),
                title = stepUnit.title
            )
        }
    }

}
