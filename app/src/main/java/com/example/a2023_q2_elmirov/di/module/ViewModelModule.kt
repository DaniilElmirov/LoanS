package com.example.a2023_q2_elmirov.di.module

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.di.annotation.ViewModelKey
import com.example.a2023_q2_elmirov.presentation.RegistrationViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel
}