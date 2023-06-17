package com.example.a2023_q2_elmirov.domain.entity

data class LoanConditions(
    val maxAmount: Long,
    val percent: Double,
    val period: Int,
)
