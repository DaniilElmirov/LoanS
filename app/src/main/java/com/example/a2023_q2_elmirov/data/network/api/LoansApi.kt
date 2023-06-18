package com.example.a2023_q2_elmirov.data.network.api

import com.example.a2023_q2_elmirov.data.network.model.AuthModel
import com.example.a2023_q2_elmirov.data.network.model.LoanConditionsModel
import com.example.a2023_q2_elmirov.data.network.model.LoanModel
import com.example.a2023_q2_elmirov.data.network.model.LoanRequestModel
import com.example.a2023_q2_elmirov.data.network.model.UserModel
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface LoansApi {
    @POST("login")
    suspend fun login(@Body authModel: AuthModel): Response<ResponseBody>

    @POST("registration")
    suspend fun register(@Body authModel: AuthModel): UserModel

    @POST("loans")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body loanRequestModel: LoanRequestModel,
    ): Response<LoanModel>

    @GET("loans/conditions")
    suspend fun getLoanConditions(@Header("Authorization") token: String): Response<LoanConditionsModel>

    @GET("loans/{id}")
    suspend fun getLoanById(@Header("Authorization") token: String, @Path("id") id: Long): LoanModel

    @GET("/loans/all")
    suspend fun getAll(@Header("Authorization") token: String): List<LoanModel>
}