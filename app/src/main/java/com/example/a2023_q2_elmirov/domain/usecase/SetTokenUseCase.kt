package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(
    private val repository: TokenRepository,
) {

    operator fun invoke(token: AccessToken) {
        repository.set(token)
    }
}