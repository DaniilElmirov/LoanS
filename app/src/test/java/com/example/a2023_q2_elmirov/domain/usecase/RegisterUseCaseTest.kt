package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class RegisterUseCaseTest {

    private val repository: AuthRepository = mock()
    private val useCase = RegisterUseCase(repository)

    private val auth = Data.auth
    private val user = Data.user

    @Test
    fun `invoke EXPECTED get user`() = runTest {
        whenever(repository.register(auth)) doReturn user

        val expected = user
        val actual = useCase(auth)

        Assertions.assertEquals(expected, actual)
    }
}