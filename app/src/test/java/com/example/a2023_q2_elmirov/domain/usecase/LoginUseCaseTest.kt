package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginUseCaseTest {

    private val repository: AuthRepository = mock()
    private val useCase = LoginUseCase(repository)

    private val auth = Data.auth
    private val token = Data.token

    @Test
    fun `invoke EXPECT get token`() = runTest {
        whenever(repository.login(auth)) doReturn token

        val expected = token
        val actual = useCase(auth)

        assertEquals(expected, actual)
    }
}