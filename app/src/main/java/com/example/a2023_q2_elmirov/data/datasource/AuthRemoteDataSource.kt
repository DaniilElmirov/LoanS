package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.AuthConverter
import com.example.a2023_q2_elmirov.data.converter.UserConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthRemoteDataSource {

    suspend fun login(auth: Auth): String

    suspend fun register(auth: Auth): User
}

class AuthRemoteDataSourceImpl @Inject constructor(
    private val userConverter: UserConverter,
    private val authConverter: AuthConverter,
    private val api: LoansApi,
    private val dispatcherIo: CoroutineDispatcher,
) : AuthRemoteDataSource {

    override suspend fun login(auth: Auth): String = withContext(dispatcherIo) {
        api.login(authConverter(auth)).body()!!.string()
    }

    override suspend fun register(auth: Auth): User = withContext(dispatcherIo) {
        userConverter(api.register(authConverter(auth)))
    }
}