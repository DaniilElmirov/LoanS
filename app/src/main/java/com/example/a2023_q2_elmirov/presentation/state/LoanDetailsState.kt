package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.Loan

sealed interface LoanDetailsState {

    object Initial : LoanDetailsState

    object Loading : LoanDetailsState

    data class Content(val loan: Loan) : LoanDetailsState

    data class Error(val errorType: ErrorType) : LoanDetailsState
}