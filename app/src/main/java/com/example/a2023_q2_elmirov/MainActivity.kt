package com.example.a2023_q2_elmirov

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import com.example.a2023_q2_elmirov.domain.usecase.LoginUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as LoansApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = Auth(
            "mama1",
            "daniil"
        )

        GlobalScope.launch {
            try {
                Log.d("TAG T", LoginUseCase(authRepository).invoke(auth))
            } catch (e: Exception) {
                Log.d("TAG E", e.toString())
            }
        }
    }
}