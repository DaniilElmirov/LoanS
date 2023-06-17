package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User
import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
) : AuthRepository {
    override suspend fun login(auth: Auth): String =
        authRemoteDataSource.login(auth)

    override suspend fun register(auth: Auth): User = authRemoteDataSource.register(auth)
}