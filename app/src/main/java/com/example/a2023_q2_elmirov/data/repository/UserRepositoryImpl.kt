package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.AuthDataSource
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User
import com.example.a2023_q2_elmirov.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : UserRepository {
    override suspend fun login(auth: Auth): String = authDataSource.login(auth)

    override suspend fun register(auth: Auth): User = authDataSource.register(auth)
}