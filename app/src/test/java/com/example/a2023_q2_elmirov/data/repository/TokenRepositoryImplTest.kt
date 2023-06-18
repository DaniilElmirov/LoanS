package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_elmirov.utils.Data
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class TokenRepositoryImplTest {

    private val dataSource: TokenLocalDataSource = mock()
    private val repository = TokenRepositoryImpl(dataSource)

    private val accessToken = Data.accessToken
    private val accessTokenDefault = Data.accessTokenDefault

    @Test
    fun `get EXPECT access token`() {
        whenever(dataSource.get()) doReturn accessToken

        val expected = accessToken
        val actual = repository.get()

        assertEquals(expected, actual)
    }

    @Test
    fun `get when shared is empty EXPECT access token with default value`() {
        whenever(dataSource.get()) doReturn accessTokenDefault

        val expected = accessTokenDefault
        val actual = repository.get()

        assertEquals(expected, actual)
    }

    @Test
    fun `set EXPECT set in shared pref`() {
        repository.set(accessToken)

        verify(dataSource).set(accessToken)
    }
}