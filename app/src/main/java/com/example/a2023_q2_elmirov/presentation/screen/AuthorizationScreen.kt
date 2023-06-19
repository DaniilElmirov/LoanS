package com.example.a2023_q2_elmirov.presentation.screen

import com.example.a2023_q2_elmirov.ui.AuthorizationFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getAuthorizationScreen(): FragmentScreen = FragmentScreen {
    AuthorizationFragment()
}