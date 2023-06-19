package com.example.a2023_q2_elmirov.presentation.screen

import com.example.a2023_q2_elmirov.ui.UserOptionsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getUserOptionsScreen(): FragmentScreen = FragmentScreen {
    UserOptionsFragment()
}