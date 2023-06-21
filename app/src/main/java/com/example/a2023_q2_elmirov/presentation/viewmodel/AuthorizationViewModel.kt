package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.AccessToken
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.usecase.LoginUseCase
import com.example.a2023_q2_elmirov.domain.usecase.SetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.AuthorizationRouter
import com.example.a2023_q2_elmirov.presentation.state.AuthorizationState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class AuthorizationViewModel @Inject constructor(
    private val router: AuthorizationRouter,
    private val loginUseCase: LoginUseCase,
    private val setTokenUseCase: SetTokenUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<AuthorizationState>(AuthorizationState.Initial)
    val state: LiveData<AuthorizationState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException -> _state.value = AuthorizationState.Error(ErrorType.INTERNET)
            is HttpException -> {
                when {
                    //TODO спросить как правильно сбросить state
                    (exception.code() == 400) ->
                        _state.value = AuthorizationState.Error(ErrorType.HTTP400)

                    (exception.code() == 401) ->
                        _state.value = AuthorizationState.Error(ErrorType.HTTP401)

                    (exception.code() == 403) ->
                        _state.value = AuthorizationState.Error(ErrorType.HTTP403)

                    (exception.code() == 404) ->
                        _state.value = AuthorizationState.Error(ErrorType.HTTP404)
                }
            }

            else -> _state.value = AuthorizationState.Error(ErrorType.UNKNOWN)
        }
    }

    fun authorization(auth: Auth) {

        if (!validateInput(auth))
            _state.value = AuthorizationState.Error(ErrorType.INVALID)
        else {
            _state.value = AuthorizationState.Loading

            viewModelScope.launch(handleError) {
                val token = loginUseCase(auth)
                setTokenUseCase(AccessToken(token))
                openUserOptions()
            }

            _state.value = AuthorizationState.Initial
        }
    }

    private fun validateInput(auth: Auth): Boolean {
        var result = true

        if (auth.name.isBlank() || auth.password.isBlank())
            result = false

        return result
    }

    private fun openUserOptions() {
        router.openUserOptions()
    }
}