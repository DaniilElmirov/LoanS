package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetTokenUseCaseTest {

    private val repository: TokenRepository = mock()
    private val useCase = GetTokenUseCase(repository)

    private val accessToken = Data.accessToken

    @Test
    fun `invoke EXPECT access token`() = runTest {
        whenever(repository.get()) doReturn accessToken

        val expected = accessToken
        val actual = useCase()

        assertEquals(expected, actual)
    }
}