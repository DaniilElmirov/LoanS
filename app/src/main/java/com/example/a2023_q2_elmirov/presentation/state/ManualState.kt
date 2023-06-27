package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ManualStep

sealed interface ManualState {

    object Initial : ManualState

    data class Content(val step: ManualStep) : ManualState
}