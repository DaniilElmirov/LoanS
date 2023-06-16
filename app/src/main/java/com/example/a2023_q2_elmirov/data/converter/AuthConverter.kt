package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.data.network.model.AuthModel
import com.example.a2023_q2_elmirov.domain.entity.Auth
import javax.inject.Inject

class AuthConverter @Inject constructor() {

    operator fun invoke(from: Auth): AuthModel = AuthModel(
        name = from.name,
        password = from.password,
    )
}