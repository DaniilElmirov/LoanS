package com.example.a2023_q2_elmirov.di.component

import android.app.Application
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.di.module.DataModule
import com.example.a2023_q2_elmirov.di.module.NavigationModule
import com.example.a2023_q2_elmirov.di.module.ViewModelModule
import com.example.a2023_q2_elmirov.presentation.activity.MainActivity
import com.example.a2023_q2_elmirov.ui.ApplyLoanFragment
import com.example.a2023_q2_elmirov.ui.AuthorizationFragment
import com.example.a2023_q2_elmirov.ui.EntryFragment
import com.example.a2023_q2_elmirov.ui.LoansFragment
import com.example.a2023_q2_elmirov.ui.RegistrationFragment
import com.example.a2023_q2_elmirov.ui.UserOptionsFragment
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

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}