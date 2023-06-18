package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.AuthConverter
import com.example.a2023_q2_elmirov.data.converter.UserConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.utils.Data
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class AuthRemoteDataSourceImplTest {

    private val userConverter: UserConverter = mock()
    private val authConverter: AuthConverter = mock()
    private val api: LoansApi = mock()

    private val token = Data.token

    private val auth = Data.auth
    private val authModel = Data.authModel

    private val userModel = Data.userModel
    private val user = Data.user

    private val responseBody = token.toResponseBody(null)
    private val response = Response.success(responseBody)

    @Test
    fun `login EXPECT token`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = AuthRemoteDataSourceImpl(userConverter, authConverter, api, dispatcher)

        whenever(authConverter(auth)) doReturn authModel
        whenever(api.login(authModel)) doReturn response

        val expected = token
        val actual = dataSource.login(auth)

        assertEquals(expected, actual)
    }

    @Test
    fun `register EXPECT user`() = runTest {
        val dispatcher = UnconfinedTestDispatcher(testScheduler)
        val dataSource = AuthRemoteDataSourceImpl(userConverter, authConverter, api, dispatcher)

        whenever(userConverter(userModel)) doReturn user
        whenever(authConverter(auth)) doReturn authModel
        whenever(api.register(authModel)) doReturn userModel

        val expected = user
        val actual = dataSource.register(auth)

        assertEquals(expected, actual)
    }
}