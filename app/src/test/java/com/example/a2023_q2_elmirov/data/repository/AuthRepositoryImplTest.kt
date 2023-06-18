package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthRepositoryImplTest {

    private val dataSource: AuthRemoteDataSource = mock()
    private val repository = AuthRepositoryImpl(dataSource)

    private val auth = Data.auth
    private val token = Data.token
    private val user = Data.user

    @Test
    fun `login EXPECT get bearer`() = runTest {
        whenever(dataSource.login(auth)) doReturn token

        val expected = token
        val actual = repository.login(auth)

        assertEquals(expected, actual)
    }

    @Test
    fun `register EXPECT get user`() = runTest {
        whenever(dataSource.register(auth)) doReturn user

        val expected = user
        val actual = repository.register(auth)

        assertEquals(expected, actual)
    }
}