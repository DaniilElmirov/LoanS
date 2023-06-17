package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import javax.inject.Inject

class GetLoanConditionsUseCase @Inject constructor(
    private val loanRepository: LoanRepository,
) {

    suspend operator fun invoke(token: String): LoanConditions =
        loanRepository.getLoanConditions(token)
}