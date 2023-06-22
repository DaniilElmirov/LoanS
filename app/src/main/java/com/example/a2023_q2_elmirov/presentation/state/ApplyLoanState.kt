package com.example.a2023_q2_elmirov.presentation.state

import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus

sealed interface ApplyLoanState {

    object Initial : ApplyLoanState

    object Loading : ApplyLoanState

    data class Content(val loanConditions: LoanConditions) : ApplyLoanState

    data class Result(val status: LoanStatus) : ApplyLoanState

    data class Error(val errorType: ErrorType) : ApplyLoanState
}
