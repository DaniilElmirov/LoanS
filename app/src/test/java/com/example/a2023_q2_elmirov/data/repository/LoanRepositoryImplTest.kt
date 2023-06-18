package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoanRepositoryImplTest {

    private val dataSource: LoanRemoteDataSource = mock()
    private val repository = LoanRepositoryImpl(dataSource)

    private val token = Data.token

    private val loan = Data.loan
    private val listLoan = Data.listLoan
    private val loanRequest = Data.loanRequest

    private val loanConditions = Data.loanConditions

    @Test
    fun `create EXPECT loan`() = runTest {
        whenever(dataSource.create(token, loanRequest)) doReturn loan

        val expected = loan
        val actual = repository.create(token, loanRequest)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan conditions EXPECT loan conditions`() = runTest {
        whenever(dataSource.getLoanConditions(token)) doReturn loanConditions

        val expected = loanConditions
        val actual = repository.getLoanConditions(token)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan by id EXPECT loan`() = runTest {
        val id = 0L

        whenever(dataSource.getLoanById(token, id)) doReturn loan

        val expected = loan
        val actual = repository.getLoanById(token, id)

        assertEquals(expected, actual)
    }

    @Test
    fun `get all EXPECT list loan`() = runTest {
        whenever(dataSource.getAll(token)) doReturn listLoan

        val expected = listLoan
        val actual = repository.getAll(token)

        assertEquals(expected, actual)
    }
}