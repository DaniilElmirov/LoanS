package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.EntryRouter
import com.example.a2023_q2_elmirov.presentation.screen.getAuthorizationScreen
import com.example.a2023_q2_elmirov.presentation.screen.getRegistrationScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class EntryRouterImpl @Inject constructor(
    private val router: Router,
): EntryRouter {

    override fun openAuthorization() {
        router.navigateTo(getAuthorizationScreen())
    }

    override fun openRegistration() {
        router.navigateTo(getRegistrationScreen())
    }
}