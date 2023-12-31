package com.example.a2023_q2_elmirov.di.module

import com.example.a2023_q2_elmirov.di.annotation.ApplicationScope
import com.example.a2023_q2_elmirov.navigation.ApplyLoanRouterImpl
import com.example.a2023_q2_elmirov.navigation.AuthorizationRouterImpl
import com.example.a2023_q2_elmirov.navigation.EntryRouterImpl
import com.example.a2023_q2_elmirov.navigation.LoanDetailsRouterImpl
import com.example.a2023_q2_elmirov.navigation.LoansRouterImpl
import com.example.a2023_q2_elmirov.navigation.RegistrationRouterImpl
import com.example.a2023_q2_elmirov.navigation.UserOptionsRouterImpl
import com.example.a2023_q2_elmirov.presentation.router.ApplyLoanRouter
import com.example.a2023_q2_elmirov.presentation.router.AuthorizationRouter
import com.example.a2023_q2_elmirov.presentation.router.EntryRouter
import com.example.a2023_q2_elmirov.presentation.router.LoanDetailsRouter
import com.example.a2023_q2_elmirov.presentation.router.LoansRouter
import com.example.a2023_q2_elmirov.presentation.router.RegistrationRouter
import com.example.a2023_q2_elmirov.presentation.router.UserOptionsRouter
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface NavigationModule {

    companion object {
        private val cicerone: Cicerone<Router> = create()

        @Provides
        @ApplicationScope
        fun provideRouter(): Router = cicerone.router

        @Provides
        @ApplicationScope
        fun provideNavigatorHolder(): NavigatorHolder = cicerone.getNavigatorHolder()
    }

    @ApplicationScope
    @Binds
    fun bindEntryRouter(impl: EntryRouterImpl): EntryRouter

    @ApplicationScope
    @Binds
    fun bindAuthorizationRouter(impl: AuthorizationRouterImpl): AuthorizationRouter

    @ApplicationScope
    @Binds
    fun bindRegistrationRouter(impl: RegistrationRouterImpl): RegistrationRouter

    @ApplicationScope
    @Binds
    fun bindUserOptionsRouter(impl: UserOptionsRouterImpl): UserOptionsRouter

    @ApplicationScope
    @Binds
    fun bindApplyLoanRouter(impl: ApplyLoanRouterImpl): ApplyLoanRouter

    @ApplicationScope
    @Binds
    fun bindLoansRouter(impl: LoansRouterImpl): LoansRouter

    @ApplicationScope
    @Binds
    fun bindLoanDetailsRouter(impl: LoanDetailsRouterImpl): LoanDetailsRouter
}