package com.example.a2023_q2_elmirov.utils

import com.example.a2023_q2_elmirov.data.network.model.AuthModel
import com.example.a2023_q2_elmirov.data.network.model.UserModel
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User

object Data {
    val auth = Auth(name = "NAME", password = "PASSWORD")
    val authModel = AuthModel(name = "NAME", password = "PASSWORD")

    val userModel = UserModel(name = "USER", role = "ROLE")
    val user = User(name = "USER", role = "ROLE")

    const val token = "TOKEN"
}