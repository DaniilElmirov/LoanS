package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.domain.usecase.LoginUseCase
import com.example.a2023_q2_elmirov.presentation.router.AuthorizationRouter
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val router: AuthorizationRouter,
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    fun openUserOptions() {
        router.openUserOptions()
    }
}