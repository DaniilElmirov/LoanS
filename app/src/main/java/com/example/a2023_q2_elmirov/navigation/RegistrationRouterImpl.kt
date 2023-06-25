package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.RegistrationRouter
import com.example.a2023_q2_elmirov.presentation.screen.getUserOptionsScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class RegistrationRouterImpl @Inject constructor(
    private val router: Router,
) : RegistrationRouter {

    override fun openUserOptions() {
        router.navigateTo(getUserOptionsScreen())
    }
}