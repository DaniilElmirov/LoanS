package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.AuthConverter
import com.example.a2023_q2_elmirov.data.converter.UserConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthDataSourceTest {

    private val userConverter: UserConverter = mock()
    private val authConverter: AuthConverter = mock()
    private val api: LoansApi = mock()
    private val datasource = AuthDataSourceImpl(userConverter, authConverter, api)

    private val auth = Data.auth
    private val token = Data.token

    private val userModel = Data.userModel
    private val user = Data.user

    @Test
    fun `login EXPECT get bearer`() = runTest {
        whenever(api.login(authConverter(auth))) doReturn token

        val expected = token
        val actual = datasource.login(auth)

        assertEquals(expected, actual)
    }

    @Test
    fun `register EXPECT get user`() = runTest {
        whenever(api.register(authConverter(auth))) doReturn userModel
        whenever(userConverter(userModel)) doReturn user

        val expected = user
        val actual = datasource.register(auth)

        assertEquals(expected, actual)
    }
}