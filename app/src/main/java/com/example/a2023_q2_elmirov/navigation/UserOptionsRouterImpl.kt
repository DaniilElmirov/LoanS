package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.UserOptionsRouter
import com.example.a2023_q2_elmirov.presentation.screen.getApplyLoanScreen
import com.example.a2023_q2_elmirov.presentation.screen.getLoansScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class UserOptionsRouterImpl @Inject constructor(
    private val router: Router,
) : UserOptionsRouter {

    override fun openApplyLoan() {
        router.navigateTo(getApplyLoanScreen())
    }

    override fun openLoans() {
        router.navigateTo(getLoansScreen())
    }
}