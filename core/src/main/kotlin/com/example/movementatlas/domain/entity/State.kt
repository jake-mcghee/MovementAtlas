package com.example.movementatlas.domain.entity

sealed class State {
    data class Solo(val soloState: SoloState) : State()
    data class Partner(val partnerState: PartnerState) : State()
}
