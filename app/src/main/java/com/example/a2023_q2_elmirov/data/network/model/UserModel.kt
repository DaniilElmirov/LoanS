package com.example.a2023_q2_elmirov.data.network.model

import com.example.a2023_q2_elmirov.domain.entity.UserRole

data class UserModel(
    val name: String,
    val role: UserRole,
)
