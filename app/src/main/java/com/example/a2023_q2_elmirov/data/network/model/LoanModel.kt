package com.example.a2023_q2_elmirov.data.network.model

import com.example.a2023_q2_elmirov.domain.entity.LoanStatus
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class LoanModel(
    val amount: Int,
    val firstName: String,
    val lastName: String,
    val percent: Double,
    val period: Int,
    val phoneNumber: String,
    val id: Long,
    val date: LocalDateTime,
    @field:SerializedName("state") val status: LoanStatus,
)
