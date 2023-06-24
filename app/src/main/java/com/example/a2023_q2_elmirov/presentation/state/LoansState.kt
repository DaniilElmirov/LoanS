package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.Loan

sealed interface LoansState {

    object Initial : LoansState

    object Loading : LoansState

    data class Content(val loans: List<Loan>) : LoansState

    data class Error(val errorType: ErrorType) : LoansState
}