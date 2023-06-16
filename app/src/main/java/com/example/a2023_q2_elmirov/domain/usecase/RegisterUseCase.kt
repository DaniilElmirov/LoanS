package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.User
import com.example.a2023_q2_elmirov.domain.repository.UserRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: UserRepository,
) {
    suspend operator fun invoke(auth: Auth): User =
        repository.register(auth)
}