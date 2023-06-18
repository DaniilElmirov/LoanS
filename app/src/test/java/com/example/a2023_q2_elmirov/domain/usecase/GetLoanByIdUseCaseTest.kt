package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLoanByIdUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetLoanByIdUseCase(repository)

    private val token = Data.token
    private val id = 0L
    private val loan = Data.loan

    @Test
    fun `invoke EXPECT loan`() = runTest {
        whenever(repository.getLoanById(token, id)) doReturn loan

        val expected = loan
        val actual = useCase(token, id)

        assertEquals(expected, actual)
    }
}