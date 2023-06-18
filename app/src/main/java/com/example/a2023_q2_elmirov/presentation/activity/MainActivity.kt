package com.example.a2023_q2_elmirov.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as LoansApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}