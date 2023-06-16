package com.example.a2023_q2_elmirov.domain.repository

import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User

interface UserRepository {

    suspend fun login(auth: Auth): String

    suspend fun register(auth: Auth): User
}