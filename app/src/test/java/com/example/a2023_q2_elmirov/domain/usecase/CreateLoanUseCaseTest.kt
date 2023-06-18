package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class CreateLoanUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = CreateLoanUseCase(repository)

    private val token = Data.token

    private val loan = Data.loan
    private val loanRequest = Data.loanRequest

    @Test
    fun `invoke EXPECT loan`() = runTest {
        whenever(repository.create(token, loanRequest)) doReturn loan

        val expected = loan
        val actual = useCase(token, loanRequest)

        assertEquals(expected, actual)
    }
}