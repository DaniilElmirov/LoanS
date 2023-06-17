package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import javax.inject.Inject

class CreateLoanUseCase @Inject constructor(
    private val repository: LoanRepository,
) {

    suspend operator fun invoke(token: String, loanRequest: LoanRequest): Loan =
        repository.create(token, loanRequest)
}