package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.LoansRouter
import com.example.a2023_q2_elmirov.presentation.screen.getEntryScreen
import com.example.a2023_q2_elmirov.presentation.screen.getLoanDetailsScreen
import com.example.a2023_q2_elmirov.presentation.screen.getUserOptionsScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class LoansRouterImpl @Inject constructor(
    private val router: Router,
) : LoansRouter {

    override fun openLoanDetails(loanId: Long) {
        router.navigateTo(getLoanDetailsScreen(loanId))
    }

    override fun backToEntry() {
        router.backTo(getEntryScreen())
    }

    override fun backToUerOptions() {
        router.backTo(getUserOptionsScreen())
    }
}