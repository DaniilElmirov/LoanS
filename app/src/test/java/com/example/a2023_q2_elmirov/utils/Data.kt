package com.example.a2023_q2_elmirov.utils

import com.example.a2023_q2_elmirov.data.network.model.AuthModel
import com.example.a2023_q2_elmirov.data.network.model.LoanConditionsModel
import com.example.a2023_q2_elmirov.data.network.model.LoanModel
import com.example.a2023_q2_elmirov.data.network.model.LoanRequestModel
import com.example.a2023_q2_elmirov.data.network.model.UserModel
import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus
import com.example.a2023_q2_elmirov.domain.entity.User
import com.example.a2023_q2_elmirov.domain.entity.UserRole

object Data {
    val auth = Auth(name = "NAME", password = "PASSWORD")
    val authModel = AuthModel(name = "NAME", password = "PASSWORD")

    val user = User(name = "USER", role = UserRole.USER)
    val userModel = UserModel(name = "USER", role = UserRole.USER)

    val loanConditionsModel = LoanConditionsModel(
        maxAmount = 14000,
        period = 31,
        percent = 120.5,
    )
    val loanConditions = LoanConditions(
        maxAmount = 14000,
        period = 31,
        percent = 120.5,
    )

    val loanModel = LoanModel(
        amount = 14000,
        firstName = " firstName",
        lastName = "lastName",
        percent = 120.5,
        period = 31,
        phoneNumber = "8999",
        id = 92,
        date = "date",
        status = LoanStatus.APPROVED,
    )
    val listLoanModel = listOf(loanModel, loanModel, loanModel)

    val loan = Loan(
        amount = 14000,
        firstName = " firstName",
        lastName = "lastName",
        percent = 120.5,
        period = 31,
        phoneNumber = "8999",
        id = 92,
        date = "date",
        status = LoanStatus.APPROVED,
    )
    val listLoan = listOf(loan, loan, loan)

    val loanRequestModel = LoanRequestModel(
        amount = 25000,
        firstName = " firstName",
        lastName = "lastName",
        percent = 120.5,
        period = 31,
        phoneNumber = "8999",
    )
    val loanRequest = LoanRequest(
        amount = 25000,
        firstName = " firstName",
        lastName = "lastName",
        percent = 120.5,
        period = 31,
        phoneNumber = "8999",
    )

    const val token = "TOKEN"
    private const val defaultValue = "default value"

    val accessToken = AccessToken(accessToken = token)
    val accessTokenDefault = AccessToken(accessToken = defaultValue)
}