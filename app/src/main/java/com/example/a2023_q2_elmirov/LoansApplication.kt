package com.example.a2023_q2_elmirov

import android.app.Application
import com.example.a2023_q2_elmirov.di.component.DaggerApplicationComponent

class LoansApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}