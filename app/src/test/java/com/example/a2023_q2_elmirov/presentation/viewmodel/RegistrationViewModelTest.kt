package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.Observer
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.usecase.LoginUseCase
import com.example.a2023_q2_elmirov.domain.usecase.RegisterUseCase
import com.example.a2023_q2_elmirov.domain.usecase.SetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.RegistrationRouter
import com.example.a2023_q2_elmirov.presentation.state.RegistrationState
import com.example.a2023_q2_elmirov.utils.Data
import com.example.a2023_q2_elmirov.utils.InstantTaskExecutorExtension
import com.example.a2023_q2_elmirov.utils.TestCoroutineExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(
    MockitoExtension::class,
    InstantTaskExecutorExtension::class,
    TestCoroutineExtension::class
)
class RegistrationViewModelTest {

    private val router: RegistrationRouter = mock()
    private val registerUseCase: RegisterUseCase = mock()
    private val loginUseCase: LoginUseCase = mock()
    private val setTokenUseCase: SetTokenUseCase = mock()

    private val viewModel = RegistrationViewModel(
        router,
        registerUseCase,
        loginUseCase,
        setTokenUseCase
    )

    private val stateObserver: Observer<RegistrationState> = mock()

    private val token = Data.token

    private val user = Data.user

    private val auth = Data.auth
    private val invalidAuth = Data.invalidAuth

    @Test
    fun `view model created EXPECT initial state`() {
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(RegistrationState.Initial)
    }

    @Test
    fun `registration with right auth EXPECT loading state and open user options`() = runTest {
        whenever(registerUseCase(auth)) doReturn user
        whenever(loginUseCase(auth)) doReturn token

        viewModel.state.observeForever(stateObserver)
        viewModel.registration(auth)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(RegistrationState.Initial)
            verify(stateObserver).onChanged(RegistrationState.Loading)
        }
        verify(router).openUserOptions()
    }

    @Test
    fun `registration with empty field EXPECT error state invalid error type`() {
        viewModel.state.observeForever(stateObserver)
        viewModel.registration(invalidAuth)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(RegistrationState.Initial)
            verify(stateObserver).onChanged(RegistrationState.Error(ErrorType.INVALID))
        }
    }
}