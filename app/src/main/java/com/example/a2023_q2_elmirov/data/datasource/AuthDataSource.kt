package com.example.a2023_q2_elmirov.data.datasource

import com.example.a2023_q2_elmirov.data.converter.AuthConverter
import com.example.a2023_q2_elmirov.data.converter.UserConverter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User
import javax.inject.Inject

interface AuthDataSource {

    suspend fun login(auth: Auth): String

    suspend fun register(auth: Auth): User
}

class AuthDataSourceImpl @Inject constructor(
    private val userConverter: UserConverter,
    private val authConverter: AuthConverter,
    private val api: LoansApi,
) : AuthDataSource {

    override suspend fun login(auth: Auth): String =
        api.login(authConverter(auth))

    override suspend fun register(auth: Auth): User =
        userConverter(api.register(authConverter(auth)))
}