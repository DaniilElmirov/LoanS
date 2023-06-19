package com.example.a2023_q2_elmirov.navigation

import com.example.a2023_q2_elmirov.presentation.router.AuthorizationRouter
import com.example.a2023_q2_elmirov.presentation.screen.getUserOptionsScreen
import com.github.terrakok.cicerone.Router
import javax.inject.Inject

class AuthorizationRouterImpl @Inject constructor(
    private val router: Router,
) : AuthorizationRouter {

    override fun openUserOptions() {
        router.navigateTo(getUserOptionsScreen())
    }
}