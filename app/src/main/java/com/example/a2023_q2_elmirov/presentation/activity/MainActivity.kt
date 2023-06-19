package com.example.a2023_q2_elmirov.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.R
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = AppNavigator(this, R.id.fragment_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as LoansApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }
}