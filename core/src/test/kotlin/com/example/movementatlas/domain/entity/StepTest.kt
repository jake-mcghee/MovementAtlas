package com.example.movementatlas.domain.entity

import org.junit.Assert.*
import org.junit.Test

class StepTest {

    @Test
    fun `Step is created with all required properties for solo`() {
        // Given
        val id = "step-1"
        val name = "Left to Right"
        val tags = listOf("beginner", "solo")
        val type = StepType.SOLO
        val weightFootFrom = WeightFoot.LEFT
        val weightFootTo = WeightFoot.RIGHT
        
        // When
        val step = Step(
            id = id,
            name = name,
            tags = tags,
            type = type,
            weightFootFrom = weightFootFrom,
            weightFootTo = weightFootTo
        )
        
        // Then
        assertEquals(id, step.id)
        assertEquals(name, step.name)
        assertEquals(tags, step.tags)
        assertEquals(type, step.type)
        assertEquals(weightFootFrom, step.weightFootFrom)
        assertEquals(weightFootTo, step.weightFootTo)
    }

    @Test
    fun `Step is created with all required properties for partner`() {
        // Given
        val id = "step-1"
        val name = "Partner Step"
        val tags = listOf("beginner", "partner")
        val type = StepType.PARTNER
        val leadFrom = WeightFoot.LEFT
        val leadTo = WeightFoot.RIGHT
        val followFrom = WeightFoot.RIGHT
        val followTo = WeightFoot.LEFT
        
        // When
        val step = Step(
            id = id,
            name = name,
            tags = tags,
            type = type,
            leadFrom = leadFrom,
            leadTo = leadTo,
            followFrom = followFrom,
            followTo = followTo
        )
        
        // Then
        assertEquals(id, step.id)
        assertEquals(name, step.name)
        assertEquals(tags, step.tags)
        assertEquals(type, step.type)
        assertEquals(leadFrom, step.leadFrom)
        assertEquals(leadTo, step.leadTo)
        assertEquals(followFrom, step.followFrom)
        assertEquals(followTo, step.followTo)
    }

    @Test
    fun `Step equality is based on id`() {
        // Given
        val step1 = Step(
            id = "step-1",
            name = "Step 1",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        val step2 = Step(
            id = "step-1",
            name = "Different Name",
            tags = listOf("tag"),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.RIGHT,
            weightFootTo = WeightFoot.LEFT
        )
        val step3 = Step(
            id = "step-2",
            name = "Step 1",
            tags = emptyList(),
            type = StepType.SOLO,
            weightFootFrom = WeightFoot.LEFT,
            weightFootTo = WeightFoot.RIGHT
        )
        
        // Then
        assertEquals(step1, step2)
        assertNotEquals(step1, step3)
    }
}
