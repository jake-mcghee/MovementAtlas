package com.example.movementatlas.domain.entity

/**
 * Represents a pair of step units for partner dancing - one for the lead and one for the follow.
 * This pairs what both dancers do at the same time, making partner sequences easier to understand.
 * 
 * @param leadStepUnit The step unit for the LEAD dancer.
 * @param followStepUnit The step unit for the FOLLOW dancer.
 * @param title Optional human-readable title for this partner step unit.
 */
data class PartnerStepUnit(
    val leadStepUnit: StepUnit,
    val followStepUnit: StepUnit,
    val title: String? = null
)
