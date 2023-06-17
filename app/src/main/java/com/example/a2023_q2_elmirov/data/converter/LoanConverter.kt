package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.data.network.model.LoanModel
import com.example.a2023_q2_elmirov.domain.entity.Loan
import javax.inject.Inject

class LoanConverter @Inject constructor() {

    operator fun invoke(from: LoanModel): Loan = Loan(
        amount = from.amount,
        firstName = from.firstName,
        lastName = from.lastName,
        percent = from.percent,
        period = from.period,
        phoneNumber = from.phoneNumber,
        id = from.id,
        date = from.date,
        status = from.status,
    )
}