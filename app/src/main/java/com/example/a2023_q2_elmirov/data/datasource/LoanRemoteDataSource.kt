package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.LoanConditionsConverter
import com.example.a2023_q2_elmirov.data.converter.LoanConverter
import com.example.a2023_q2_elmirov.data.converter.LoanRequestConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LoanRemoteDataSource {

    suspend fun create(token: String, loanRequest: LoanRequest): Loan

    suspend fun getLoanConditions(token: String): LoanConditions

    suspend fun getLoanById(token: String, id: Long): Loan
}

class LoanRemoteDataSourceImpl @Inject constructor(
    private val loanConverter: LoanConverter,
    private val loanRequestConverter: LoanRequestConverter,
    private val loanConditionsConverter: LoanConditionsConverter,
    private val api: LoansApi,
    private val dispatcherIo: CoroutineDispatcher,
) : LoanRemoteDataSource {

    override suspend fun create(token: String, loanRequest: LoanRequest): Loan =
        withContext(dispatcherIo) {
            loanConverter(api.create(token, loanRequestConverter(loanRequest)).body()!!)
        }

    override suspend fun getLoanConditions(token: String): LoanConditions =
        withContext(dispatcherIo) {
            loanConditionsConverter(api.getLoanConditions(token).body()!!)
        }

    override suspend fun getLoanById(token: String, id: Long): Loan =
        withContext(dispatcherIo) {
            loanConverter(api.getLoanById(token, id))
        }
}