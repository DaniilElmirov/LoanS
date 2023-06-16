package com.example.a2023_q2_elmirov.di.module

import com.example.a2023_q2_elmirov.data.datasource.AuthDataSource
import com.example.a2023_q2_elmirov.data.datasource.AuthDataSourceImpl
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.data.repository.UserRepositoryImpl
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
interface DataModule {

    companion object {
        private const val BASE_URL = "https://shiftlab.cft.ru:7777/"

        @ApplicationScope
        @Provides
        fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(httpClient)
            .build()

        @ApplicationScope
        @Provides
        fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

        @ApplicationScope
        @Provides
        fun provideLoansApi(retrofit: Retrofit): LoansApi = retrofit.create()
    }

    @ApplicationScope
    @Binds
    fun bindUserRepository(impl: UserRepositoryImpl): UserRepository

    @ApplicationScope
    @Binds
    fun bindAuthDataSource(impl: AuthDataSourceImpl): AuthDataSource
}