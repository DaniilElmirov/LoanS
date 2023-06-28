package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.usecase.LoginUseCase
import com.example.a2023_q2_elmirov.domain.usecase.RegisterUseCase
import com.example.a2023_q2_elmirov.domain.usecase.SetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.RegistrationRouter
import com.example.a2023_q2_elmirov.presentation.state.RegistrationState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class RegistrationViewModel @Inject constructor(
    private val router: RegistrationRouter,
    private val registerUseCase: RegisterUseCase,
    private val loginUseCase: LoginUseCase,
    private val setTokenUseCase: SetTokenUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<RegistrationState>(RegistrationState.Initial)
    val state: LiveData<RegistrationState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException,
            is ConnectException,
            is NoRouteToHostException,
            is SocketTimeoutException,
            -> _state.value = RegistrationState.Error(ErrorType.INTERNET)

            is HttpException -> {
                when {
                    //TODO спросить как правильно сбросить state
                    (exception.code() == 400) ->
                        _state.value = RegistrationState.Error(ErrorType.HTTP400)

                    (exception.code() == 401) ->
                        _state.value = RegistrationState.Error(ErrorType.HTTP401)

                    (exception.code() == 403) ->
                        _state.value = RegistrationState.Error(ErrorType.HTTP403)

                    (exception.code() == 404) ->
                        _state.value = RegistrationState.Error(ErrorType.HTTP404)
                }
            }

            else -> _state.value = RegistrationState.Error(ErrorType.UNKNOWN)
        }
    }

    fun registration(auth: Auth) {

        if (!validateInput(auth))
            _state.value = RegistrationState.Error(ErrorType.INVALID)
        else {
            _state.value = RegistrationState.Loading

            viewModelScope.launch(handleError) {
                registerUseCase(auth)

                val token = loginUseCase(auth)
                setTokenUseCase(AccessToken(token))

                router.openUserOptions()
            }
        }

        _state.value = RegistrationState.Initial
    }

    private fun validateInput(auth: Auth): Boolean {
        var result = true

        if (auth.name.isBlank() || auth.password.isBlank())
            result = false

        return result
    }
}