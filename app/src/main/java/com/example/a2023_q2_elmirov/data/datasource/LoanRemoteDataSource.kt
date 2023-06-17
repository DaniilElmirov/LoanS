package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.LoanConverter
import com.example.a2023_q2_elmirov.data.converter.LoanRequestConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LoanRemoteDataSource {

    suspend fun create(token: String, loanRequest: LoanRequest): Loan
}

class LoanRemoteDataSourceImpl @Inject constructor(
    private val loanConverter: LoanConverter,
    private val loanRequestConverter: LoanRequestConverter,
    private val api: LoansApi,
    private val dispatcherIo: CoroutineDispatcher,
) : LoanRemoteDataSource {

    override suspend fun create(token: String, loanRequest: LoanRequest): Loan =
        withContext(dispatcherIo) {
            loanConverter(api.create(token, loanRequestConverter(loanRequest)).body()!!)
        }
}