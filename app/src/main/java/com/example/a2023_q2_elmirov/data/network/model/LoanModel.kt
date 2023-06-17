package com.example.a2023_q2_elmirov.data.network.model

import com.example.a2023_q2_elmirov.domain.entity.LoanStatus
import com.google.gson.annotations.SerializedName

data class LoanModel(
    val amount: Long,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val id: Long,
    val date: String,//LocalDateTime, //TODO вернуть LocalDateTime, написать конвертер
    @field:SerializedName("state") val status: LoanStatus,
)
