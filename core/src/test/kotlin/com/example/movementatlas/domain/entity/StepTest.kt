package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepTest {

    @Test
    fun `Step is created with all required properties`() {
        // Given
        val id = "step-1"
        val name = "Basic Step"
        val tags = listOf("beginner", "solo")
        val preconditions = listOf<State>(State.Solo(SoloState(WeightFoot.LEFT)))
        val postState = State.Solo(SoloState(WeightFoot.RIGHT))
        val type = StepType.SOLO
        
        // When
        val step = Step(id, name, tags, preconditions, postState, type)
        
        // Then
        assertEquals(id, step.id)
        assertEquals(name, step.name)
        assertEquals(tags, step.tags)
        assertEquals(preconditions, step.preconditions)
        assertEquals(postState, step.postState)
        assertEquals(type, step.type)
    }

    @Test
    fun `Step equality is based on id`() {
        // Given
        val step1 = Step(
            id = "step-1",
            name = "Step 1",
            tags = emptyList(),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
        val step2 = Step(
            id = "step-1",
            name = "Different Name",
            tags = listOf("tag"),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.RIGHT)),
            type = StepType.PARTNER
        )
        val step3 = Step(
            id = "step-2",
            name = "Step 1",
            tags = emptyList(),
            preconditions = emptyList(),
            postState = State.Solo(SoloState(WeightFoot.LEFT)),
            type = StepType.SOLO
        )
        
        // Then
        assertEquals(step1, step2)
        assertNotEquals(step1, step3)
    }
}
