package com.example.a2023_q2_elmirov.di.component

import android.app.Application
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.MainActivity
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.di.module.DataModule
import com.example.a2023_q2_elmirov.di.module.ViewModelModule
import com.example.a2023_q2_elmirov.ui.RegistrationFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: LoansApplication)

    fun inject(activity: MainActivity)

    fun inject(fragment: RegistrationFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}