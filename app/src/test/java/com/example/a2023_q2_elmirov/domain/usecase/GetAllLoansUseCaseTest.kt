package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllLoansUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetAllLoansUseCase(repository)

    private val token = Data.token

    private val listLoan = Data.listLoan

    @Test
    fun `invoke EXPECT list loan`() = runTest {
        whenever(repository.getAll(token)) doReturn listLoan

        val expected = listLoan
        val actual = useCase(token)

        assertEquals(expected, actual)
    }
}