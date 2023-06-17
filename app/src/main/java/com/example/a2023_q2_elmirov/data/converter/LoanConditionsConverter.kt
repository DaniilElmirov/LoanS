package com.example.a2023_q2_elmirov.data.converter

import com.example.a2023_q2_elmirov.data.network.model.LoanConditionsModel
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import javax.inject.Inject

class LoanConditionsConverter @Inject constructor() {

    operator fun invoke(from: LoanConditionsModel): LoanConditions = LoanConditions(
        maxAmount = from.maxAmount,
        percent = from.percent,
        period = from.period
    )
}