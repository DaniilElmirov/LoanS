package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.LoanConditionsConverter
import com.example.a2023_q2_elmirov.data.converter.LoanConverter
import com.example.a2023_q2_elmirov.data.converter.LoanRequestConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoanRemoteDataSourceImplTest {

    private val loanConverter: LoanConverter = mock()
    private val loanRequestConverter: LoanRequestConverter = mock()
    private val loanConditionsConverter: LoanConditionsConverter = mock()
    private val api: LoansApi = mock()

    private val token = Data.token

    private val loan = Data.loan
    private val listLoan = Data.listLoan
    private val loanModel = Data.loanModel
    private val listLoanModel = Data.listLoanModel

    private val loanRequest = Data.loanRequest
    private val loanRequestModel = Data.loanRequestModel

    private val loanConditions = Data.loanConditions
    private val loanConditionsModel = Data.loanConditionsModel

    @Test
    fun `create EXPECT loan`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            loanConverter,
            loanRequestConverter,
            loanConditionsConverter,
            api,
            dispatcher
        )

        whenever(loanConverter(loanModel)) doReturn loan
        whenever(loanRequestConverter(loanRequest)) doReturn loanRequestModel
        whenever(api.create(token, loanRequestModel)) doReturn loanModel

        val expected = loan
        val actual = dataSource.create(token, loanRequest)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan conditions EXPECT loan conditions`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            loanConverter,
            loanRequestConverter,
            loanConditionsConverter,
            api,
            dispatcher
        )

        whenever(loanConditionsConverter(loanConditionsModel)) doReturn loanConditions
        whenever(api.getLoanConditions(token)) doReturn loanConditionsModel

        val expected = loanConditions
        val actual = dataSource.getLoanConditions(token)

        assertEquals(expected, actual)
    }

    @Test
    fun `get loan by id EXPECT loan`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            loanConverter,
            loanRequestConverter,
            loanConditionsConverter,
            api,
            dispatcher
        )

        val id = 0L

        whenever(loanConverter(loanModel)) doReturn loan
        whenever(api.getLoanById(token, id)) doReturn loanModel

        val expected = loan
        val actual = dataSource.getLoanById(token, id)

        assertEquals(expected, actual)
    }

    @Test
    fun `get all EXPECT list loan`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = LoanRemoteDataSourceImpl(
            loanConverter,
            loanRequestConverter,
            loanConditionsConverter,
            api,
            dispatcher
        )

        whenever(loanConverter(loanModel)) doReturn loan
        whenever(api.getAll(token)) doReturn listLoanModel

        val expected = listLoan
        val actual = dataSource.getAll(token)

        assertEquals(expected, actual)
    }
}