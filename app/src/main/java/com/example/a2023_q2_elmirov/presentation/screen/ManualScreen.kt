package com.example.a2023_q2_elmirov.presentation.screen

import com.example.a2023_q2_elmirov.ui.fragment.ManualFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getManualScreen(): FragmentScreen = FragmentScreen {
    ManualFragment()
}