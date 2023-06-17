package com.example.a2023_q2_elmirov.data.network.api

import com.example.a2023_q2_elmirov.data.network.model.AuthModel
import com.example.a2023_q2_elmirov.data.network.model.UserModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoansApi {
    @POST("login")
    suspend fun login(@Body authModel: AuthModel): Response<ResponseBody>

    @POST("registration")
    suspend fun register(@Body authModel: AuthModel): UserModel
}