package com.example.a2023_q2_elmirov.domain.repository

import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest

interface LoanRepository {

    suspend fun create(token: String, loanRequest: LoanRequest): Loan
}