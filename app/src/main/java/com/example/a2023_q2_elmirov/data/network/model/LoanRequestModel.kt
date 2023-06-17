package com.example.a2023_q2_elmirov.data.network.model

data class LoanRequestModel(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
)
