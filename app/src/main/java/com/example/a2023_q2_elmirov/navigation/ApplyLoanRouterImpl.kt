package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.ApplyLoanRouter
import com.example.a2023_q2_elmirov.presentation.screen.getEntryScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class ApplyLoanRouterImpl @Inject constructor(
    private val router: Router,
) : ApplyLoanRouter {

    override fun backToUserOptions() {
        router.exit()
    }

    override fun backToEntry() {
        router.backTo(getEntryScreen())
    }
}