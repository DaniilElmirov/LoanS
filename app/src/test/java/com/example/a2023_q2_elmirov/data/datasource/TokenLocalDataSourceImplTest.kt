package com.example.a2023_q2_elmirov.data.datasource

import android.app.Application
import android.content.Context
import com.example.a2023_q2_elmirov.utils.Data
import org.junit.Before
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

class TokenLocalDataSourceImplTest {
    companion object {
        private const val ACCESS_TOKEN_NAME = "token"
        private const val ACCESS_TOKEN_KEY = "access token"

        private const val DEFAULT_VALUE = ""
    }

    private val application: Application = mock()

    private val accessToken = Data.accessToken
    private val accessTokenDefault = Data.accessTokenDefault

    private val dataSource = TokenLocalDataSourceImpl(application)

    @Before

    @Test
    fun `get EXPECT access token`() {
        whenever(
            application
                .getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
        ) doReturn mock()
        whenever(
            application
                .getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
                .getString(ACCESS_TOKEN_KEY, DEFAULT_VALUE)
        ) doReturn accessToken.accessToken

        val expected = accessToken
        val actual = dataSource.get()

        assertEquals(expected, actual)
    }

    @Test
    fun `get when shared is empty EXPECT access token with default value`() {
        whenever(
            application
                .getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
        ) doReturn mock()
        whenever(
            application
                .getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
                .getString(ACCESS_TOKEN_KEY, DEFAULT_VALUE)
        ) doReturn accessTokenDefault.accessToken

        val expected = accessTokenDefault
        val actual = dataSource.get()

        assertEquals(expected, actual)
    }
}