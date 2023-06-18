package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import javax.inject.Inject

class LoanRepositoryImpl @Inject constructor(
    private val loanRemoteDataSource: LoanRemoteDataSource,
) : LoanRepository {

    override suspend fun create(token: String, loanRequest: LoanRequest): Loan =
        loanRemoteDataSource.create(token, loanRequest)

    override suspend fun getLoanConditions(token: String): LoanConditions =
        loanRemoteDataSource.getLoanConditions(token)

    override suspend fun getLoanById(token: String, id: Long): Loan =
        loanRemoteDataSource.getLoanById(token, id)
}