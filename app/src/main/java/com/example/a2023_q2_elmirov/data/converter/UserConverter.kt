package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.data.network.model.UserModel
import com.example.a2023_q2_elmirov.domain.entity.User
import javax.inject.Inject

class UserConverter @Inject constructor() {

    operator fun invoke(from: UserModel): User = User(
        name = from.name,
        role = from.role,
    )
}