package com.example.a2023_q2_elmirov.presentation.screen

import com.example.a2023_q2_elmirov.ui.fragment.RegistrationFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getRegistrationScreen(): FragmentScreen = FragmentScreen {
    RegistrationFragment()
}