package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.domain.usecase.GetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.EntryRouter
import javax.inject.Inject

class EntryViewModel @Inject constructor(
    private val router: EntryRouter,
    private val getTokenUseCase: GetTokenUseCase,
) : ViewModel() {

    fun openRegistration() {
        router.openRegistration()
    }

    fun openAuthorization() {
        if (getTokenUseCase().accessToken.isBlank())
            router.openAuthorization()
        else
            router.openUserOptions()
    }
}