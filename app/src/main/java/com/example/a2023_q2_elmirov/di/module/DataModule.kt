package com.example.a2023_q2_elmirov.di.module

import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSourceImpl
import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSourceImpl
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.data.repository.TokenRepositoryImpl
import com.example.a2023_q2_elmirov.data.repository.AuthRepositoryImpl
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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

        @ApplicationScope
        @Provides
        fun provideDispatcherIo(): CoroutineDispatcher = Dispatchers.IO
    }

    @ApplicationScope
    @Binds
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @ApplicationScope
    @Binds
    fun bindAuthRemoteDataSource(impl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @ApplicationScope
    @Binds
    fun bindTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @ApplicationScope
    @Binds
    fun bindTokenLocalDataSource(impl: TokenLocalDataSourceImpl): TokenLocalDataSource
}