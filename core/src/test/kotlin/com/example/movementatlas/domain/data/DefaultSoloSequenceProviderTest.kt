package com.example.movementatlas.domain.data

import com.example.movementatlas.domain.entity.*
import org.junit.Assert.*
import org.junit.Test

class DefaultSoloSequenceProviderTest {

    @Test
    fun `getCommonSequences returns list of common sequences with titles`() {
        // When
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        assertTrue(sequences.isNotEmpty())
        sequences.forEach { sequence ->
            assertNull("Common sequences should have null IDs (not persisted)", sequence.id)
            assertNotNull("Sequence should have a title", sequence.title)
            assertTrue("Sequence should have at least one step unit", sequence.stepUnits.isNotEmpty())
        }
    }

    @Test
    fun `getCommonSequences returns sequences with unique titles`() {
        // When
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        val titles = sequences.mapNotNull { it.title }
        assertEquals("All sequences should have unique titles", titles.size, titles.toSet().size)
    }

    @Test
    fun `getCommonSequences includes basic_step sequence`() {
        // When
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        val basicStep = sequences.find { it.title == "Basic Step In Place" }
        assertNotNull("Should include basic step sequence", basicStep)
        assertEquals("Basic step should have one step unit", 1, basicStep!!.stepUnits.size)
        assertEquals("Basic step should have correct title", "Basic Step In Place", basicStep.title)
    }

    @Test
    fun `getCommonSequences includes linear_basic_forward_half`() {
        // When
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        val forwardHalf = sequences.find { it.title == "Linear Basic Forward Half" }
        assertNotNull("Should include linear basic forward half", forwardHalf)
        assertEquals("Forward half should have one step unit", 1, forwardHalf!!.stepUnits.size)
        assertEquals("Forward half should have correct title", "Linear Basic Forward Half", forwardHalf.title)
    }

    @Test
    fun `getCommonSequences includes linear_basic_backward_half`() {
        // When
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()

        // Then
        val backwardHalf = sequences.find { it.title == "Linear Basic Backward Half" }
        assertNotNull("Should include linear basic backward half", backwardHalf)
        assertEquals("Backward half should have one step unit", 1, backwardHalf!!.stepUnits.size)
        assertEquals("Backward half should have correct title", "Linear Basic Backward Half", backwardHalf.title)
    }

    @Test
    fun `common sequences can compute end weight foot`() {
        // Given
        val sequences = DefaultSoloSequenceProvider.getCommonSequences()
        val startWeightFoot = WeightFoot.LEFT

        // When & Then
        sequences.forEach { sequence ->
            val endWeightFoot = sequence.computeEndWeightFoot(startWeightFoot)
            assertNotNull("Should be able to compute end weight foot", endWeightFoot)
            // Sequences with odd number of step units end on opposite foot,
            // sequences with even number end on same foot
            val expectedEndFoot = if (sequence.stepUnits.size % 2 == 1) {
                WeightFoot.RIGHT
            } else {
                WeightFoot.LEFT
            }
            assertEquals("Sequence should compute correct end foot", expectedEndFoot, endWeightFoot)
        }
    }
}
