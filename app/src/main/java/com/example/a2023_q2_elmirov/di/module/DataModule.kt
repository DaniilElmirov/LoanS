package com.example.a2023_q2_elmirov.di.module

import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSource
import com.example.a2023_q2_elmirov.data.datasource.AuthRemoteDataSourceImpl
import com.example.a2023_q2_elmirov.data.datasource.LoanRemoteDataSource
import com.example.a2023_q2_elmirov.data.datasource.LoanRemoteDataSourceImpl
import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSource
import com.example.a2023_q2_elmirov.data.datasource.TokenLocalDataSourceImpl
import com.example.a2023_q2_elmirov.data.gsonadapter.LocalDateTimeGsonAdapter
import com.example.a2023_q2_elmirov.data.network.api.LoansApi
import com.example.a2023_q2_elmirov.data.repository.AuthRepositoryImpl
import com.example.a2023_q2_elmirov.data.repository.LoanRepositoryImpl
import com.example.a2023_q2_elmirov.data.repository.TokenRepositoryImpl
import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.domain.repository.AuthRepository
import com.example.a2023_q2_elmirov.domain.repository.LoanRepository
import com.example.a2023_q2_elmirov.domain.repository.TokenRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
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
import java.time.LocalDateTime

@Module
interface DataModule {

    companion object {
        private const val BASE_URL = "https://shiftlab.cft.ru:7777/"

        @ApplicationScope
        @Provides
        fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit =
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(BASE_URL)
                .client(httpClient)
                .build()

        @ApplicationScope
        @Provides
        fun provideGson(): Gson =
            GsonBuilder()
                .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeGsonAdapter)
                .create()

        @ApplicationScope
        @Provides
        fun provideHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()

        @ApplicationScope
        @Provides
        fun provideLoansApi(retrofit: Retrofit): LoansApi =
            retrofit.create()

        @ApplicationScope
        @Provides
        fun provideDispatcherIo(): CoroutineDispatcher =
            Dispatchers.IO
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

    @ApplicationScope
    @Binds
    fun bindLoanRepository(impl: LoanRepositoryImpl): LoanRepository

    @ApplicationScope
    @Binds
    fun bindLoanRemoteDataSource(impl: LoanRemoteDataSourceImpl): LoanRemoteDataSource
}