package com.example.a2023_q2_elmirov

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.domain.usecase.GetLoanByIdUseCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var loanRepository: LoanRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as LoansApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = Auth(
            "mama1",
            "daniil"
        )

        val loanRequest = LoanRequest(
            25000,
            "string",
            "string",
            10.48,
            90,
            "string"
        )

        val token =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJEYW5paWwiLCJleHAiOjE2ODk1ODY5NDl9.OCwcJIq1PIOUXOwZcTCPesQ1fmcD4nufhSjqy0rwc6GnqY1DafzzXn4VlAEVWKcf94dETd-26XQ7ZU0ArRqGdA"

        GlobalScope.launch {
            try {
                Log.d("TAG ID", GetLoanByIdUseCase(loanRepository).invoke(token, 92).toString())
            } catch (e: Exception) {
                Log.d("TAG E", e.toString())
            }
        }
    }
}