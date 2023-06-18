package com.example.a2023_q2_elmirov.presentation

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.domain.usecase.RegisterUseCase
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

}