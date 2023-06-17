package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.data.network.model.LoanRequestModel
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import javax.inject.Inject

class LoanRequestConverter @Inject constructor() {

    operator fun invoke(from: LoanRequest): LoanRequestModel = LoanRequestModel(
        amount = from.amount,
        firstName = from.firstName,
        lastName = from.lastName,
        percent = from.percent,
        period = from.period,
        phoneNumber = from.phoneNumber,
    )
}