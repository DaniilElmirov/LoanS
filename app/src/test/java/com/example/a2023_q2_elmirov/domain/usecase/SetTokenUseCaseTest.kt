package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class SetTokenUseCaseTest {

    private val repository: TokenRepository = mock()
    private val useCase = SetTokenUseCase(repository)

    private val accessToken = Data.accessToken

    @Test
    fun `invoke EXPECT set in shared pref`() = runTest {

        useCase(accessToken)

        verify(repository).set(accessToken)
    }
}