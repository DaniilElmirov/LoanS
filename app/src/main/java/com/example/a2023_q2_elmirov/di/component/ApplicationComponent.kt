package com.example.a2023_q2_elmirov.di.component

import android.app.Application
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.di.module.DataModule
import com.example.a2023_q2_elmirov.di.module.NavigationModule
import com.example.a2023_q2_elmirov.di.module.ViewModelModule
import com.example.a2023_q2_elmirov.presentation.activity.MainActivity
import com.example.a2023_q2_elmirov.ui.fragment.ApplyLoanFragment
import com.example.a2023_q2_elmirov.ui.fragment.AuthorizationFragment
import com.example.a2023_q2_elmirov.ui.fragment.EntryFragment
import com.example.a2023_q2_elmirov.ui.fragment.LoanDetailsFragment
import com.example.a2023_q2_elmirov.ui.fragment.LoansFragment
import com.example.a2023_q2_elmirov.ui.fragment.ManualFragment
import com.example.a2023_q2_elmirov.ui.fragment.RegistrationFragment
import com.example.a2023_q2_elmirov.ui.fragment.UserOptionsFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
        NavigationModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: LoansApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: RegistrationFragment)

    fun inject(fragment: AuthorizationFragment)

    fun inject(fragment: EntryFragment)

    fun inject(fragment: UserOptionsFragment)

    fun inject(fragment: ApplyLoanFragment)

    fun inject(fragment: LoansFragment)

    fun inject(fragment: LoanDetailsFragment)

    fun inject(fragment: ManualFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}