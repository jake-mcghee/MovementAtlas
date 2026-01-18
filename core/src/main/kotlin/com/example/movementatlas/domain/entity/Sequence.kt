package com.example.movementatlas.domain.entity

data class Sequence(
    val stepUnits: List<StepUnit>,
    val startState: State,
    val endState: State
)
