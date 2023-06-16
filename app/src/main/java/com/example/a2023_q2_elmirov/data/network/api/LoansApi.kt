package com.example.a2023_q2_elmirov.data.network.api

import com.example.a2023_q2_elmirov.data.network.model.UserModel
import com.example.a2023_q2_elmirov.domain.entity.Auth
import retrofit2.http.Body
import retrofit2.http.POST

interface LoansApi {
    @POST("login")
    suspend fun login(@Body auth: Auth): String

    @POST("registration")
    suspend fun register(@Body auth: Auth): UserModel
}