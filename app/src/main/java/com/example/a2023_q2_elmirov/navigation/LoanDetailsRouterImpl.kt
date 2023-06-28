package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.LoanDetailsRouter
import com.example.a2023_q2_elmirov.presentation.screen.getEntryScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class LoanDetailsRouterImpl @Inject constructor(
    private val router: Router,
) : LoanDetailsRouter {

    override fun backToEntry() {
        router.backTo(getEntryScreen())
    }

    override fun backToLoans() {
        router.exit()
    }
}