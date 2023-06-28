package com.example.a2023_q2_elmirov.domain.repository

import com.example.a2023_q2_elmirov.domain.entity.AccessToken

interface TokenRepository {

    fun get(): AccessToken

    fun set(token: AccessToken)

    fun delete()
}