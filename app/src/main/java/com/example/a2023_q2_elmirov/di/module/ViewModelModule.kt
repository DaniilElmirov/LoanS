package com.example.a2023_q2_elmirov.di.module

import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.di.annotation.ViewModelKey
import com.example.a2023_q2_elmirov.presentation.viewmodel.ApplyLoanViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.AuthorizationViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.EntryViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.LoanDetailsViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.LoansViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ManualViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.RegistrationViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.UserOptionsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegistrationViewModel::class)
    fun bindRegistrationViewModel(viewModel: RegistrationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AuthorizationViewModel::class)
    fun bindAuthorizationViewModel(viewModel: AuthorizationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EntryViewModel::class)
    fun bindEntryViewModel(viewModel: EntryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserOptionsViewModel::class)
    fun bindUserOptionsViewModel(viewModel: UserOptionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ApplyLoanViewModel::class)
    fun bindApplyLoanViewModel(viewModel: ApplyLoanViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoansViewModel::class)
    fun bindLoansViewModel(viewModel: LoansViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoanDetailsViewModel::class)
    fun bindLoanDetailsViewModel(viewModel: LoanDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ManualViewModel::class)
    fun bindManualViewModel(viewModel: ManualViewModel): ViewModel
}