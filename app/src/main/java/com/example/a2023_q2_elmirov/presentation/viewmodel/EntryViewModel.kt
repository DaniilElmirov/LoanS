package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.presentation.router.EntryRouter
import javax.inject.Inject

class EntryViewModel @Inject constructor(
    private val router: EntryRouter,
) : ViewModel() {

    fun openRegistration() {
        router.openRegistration()
    }

    fun openAuthorization() {
        router.openAuthorization()
    }
}