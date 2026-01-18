package com.example.movementatlas.domain.entity

data class Sequence(
    val steps: List<Step>,
    val startState: State,
    val endState: State
)
