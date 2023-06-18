package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetLoanConditionsUseCaseTest {

    private val repository: LoanRepository = mock()
    private val useCase = GetLoanConditionsUseCase(repository)

    private val token = Data.token

    private val loanConditions = Data.loanConditions

    @Test
    fun `invoke EXPECT loan conditions`() = runTest {
        whenever(repository.getLoanConditions(token)) doReturn loanConditions

        val expected = loanConditions
        val actual = useCase(token)

        assertEquals(expected, actual)
    }
}