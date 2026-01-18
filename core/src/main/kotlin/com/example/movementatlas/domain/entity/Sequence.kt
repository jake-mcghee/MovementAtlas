package com.example.movementatlas.domain.entity

data class Sequence(
    val stepUnits: List<StepUnit>,
    val startWeightFoot: WeightFoot,
    val endWeightFoot: WeightFoot
)
