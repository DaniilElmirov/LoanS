package com.example.a2023_q2_elmirov.di.component

import android.app.Application
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.MainActivity
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.di.module.DataModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
    ]
)
interface ApplicationComponent {

    fun inject(application: LoansApplication)

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application,
        ): ApplicationComponent
    }
}