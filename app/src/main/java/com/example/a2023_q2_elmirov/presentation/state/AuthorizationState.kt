package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ErrorType

sealed interface AuthorizationState {

    object Initial : AuthorizationState

    object Loading : AuthorizationState

    data class Error(val errorType: ErrorType) : AuthorizationState
}
