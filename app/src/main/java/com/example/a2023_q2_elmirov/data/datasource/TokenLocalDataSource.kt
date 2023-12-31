package com.example.a2023_q2_elmirov.data.datasource

import android.app.Application
import android.content.Context
import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import javax.inject.Inject

interface TokenLocalDataSource {

    fun get(): AccessToken

    fun set(token: AccessToken)

    fun delete()
}

class TokenLocalDataSourceImpl @Inject constructor(
    private val application: Application,
) : TokenLocalDataSource {

    companion object {
        private const val ACCESS_TOKEN_NAME = "token"
        private const val ACCESS_TOKEN_KEY = "access token"

        private const val DEFAULT_VALUE = ""
    }

    override fun get(): AccessToken {
        val accessToken = application.getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
            .getString(ACCESS_TOKEN_KEY, DEFAULT_VALUE)

        return AccessToken(accessToken ?: DEFAULT_VALUE)
    }

    override fun set(token: AccessToken) {
        application.getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(ACCESS_TOKEN_KEY, token.accessToken)
            .apply()
    }

    override fun delete() {
        application.getSharedPreferences(ACCESS_TOKEN_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(ACCESS_TOKEN_KEY, DEFAULT_VALUE)
            .apply()
    }
}