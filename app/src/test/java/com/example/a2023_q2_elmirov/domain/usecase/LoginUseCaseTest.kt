package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.UserRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class LoginUseCaseTest {

    private val repository: UserRepository = mock()
    private val useCase = LoginUseCase(repository)

    private val auth = Data.auth
    private val bearer = Data.bearer

    @Test
    fun `invoke EXPECT get bearer`() = runTest {
        whenever(repository.login(auth)) doReturn bearer

        val expected = bearer
        val actual = useCase(auth)

        assertEquals(expected, actual)
    }
}