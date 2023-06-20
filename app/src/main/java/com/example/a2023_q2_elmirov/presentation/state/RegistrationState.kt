package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ErrorType

sealed interface RegistrationState {

    object Initial : RegistrationState

    object Loading : RegistrationState

    data class Error(val errorType: ErrorType) : RegistrationState
}
