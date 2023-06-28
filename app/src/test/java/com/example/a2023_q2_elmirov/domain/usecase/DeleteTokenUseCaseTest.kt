package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteTokenUseCaseTest {

    private val repository: TokenRepository = mock()
    private val useCase = DeleteTokenUseCase(repository)

    @Test
    fun `delete EXPECT delete`() = runTest {

        useCase()

        verify(repository).delete()
    }
}