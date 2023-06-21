package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.presentation.router.UserOptionsRouter
import javax.inject.Inject

class UserOptionsViewModel @Inject constructor(
    private val router: UserOptionsRouter,
) : ViewModel() {

    fun openApplyLoan() {
        router.openApplyLoan()
    }
}