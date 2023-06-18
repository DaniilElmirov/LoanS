package com.example.a2023_q2_elmirov.domain.repository

import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest

interface LoanRepository {

    suspend fun create(token: String, loanRequest: LoanRequest): Loan

    suspend fun getLoanConditions(token: String): LoanConditions

    suspend fun getLoanById(token: String, id: Long): Loan

    suspend fun getAll(token: String): List<Loan>
}