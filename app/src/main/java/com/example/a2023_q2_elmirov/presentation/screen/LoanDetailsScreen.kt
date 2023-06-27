package com.example.a2023_q2_elmirov.presentation.screen

import com.example.a2023_q2_elmirov.ui.fragment.LoanDetailsFragment
import com.github.terrakok.cicerone.androidx.FragmentScreen

fun getLoanDetailsScreen(loanId: Long): FragmentScreen = FragmentScreen {
    LoanDetailsFragment.newInstance(loanId)
}