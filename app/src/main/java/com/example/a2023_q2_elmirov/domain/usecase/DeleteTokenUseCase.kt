package com.example.a2023_q2_elmirov.domain.usecase

import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import javax.inject.Inject

class DeleteTokenUseCase @Inject constructor(
    private val repository: TokenRepository,
) {

    operator fun invoke() {
        repository.delete()
    }
}