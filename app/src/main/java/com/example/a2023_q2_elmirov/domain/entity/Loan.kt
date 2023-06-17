package com.example.a2023_q2_elmirov.domain.entity

data class Loan(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val id: Long,
    val date: String,//LocalDateTime, //TODO вернуть LocalDateTime, написать конвертер
    val status: LoanStatus,
)
