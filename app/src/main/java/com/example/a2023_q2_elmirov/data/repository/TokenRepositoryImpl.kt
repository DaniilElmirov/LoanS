package com.example.a2023_q2_elmirov.data.repository

import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenLocalDataSource: TokenLocalDataSource,
) : TokenRepository {

    override fun get(): AccessToken =
        tokenLocalDataSource.get()

    override fun set(token: AccessToken) {
        tokenLocalDataSource.set(token)
    }
}