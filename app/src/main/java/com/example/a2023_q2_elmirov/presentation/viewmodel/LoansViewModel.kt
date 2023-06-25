package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.usecase.GetAllLoansUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.LoansRouter
import com.example.a2023_q2_elmirov.presentation.state.LoansState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class LoansViewModel @Inject constructor(
    private val getAllLoansUseCase: GetAllLoansUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val router: LoansRouter,
) : ViewModel() {

    private val _state = MutableLiveData<LoansState>(LoansState.Initial)
    val state: LiveData<LoansState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException -> _state.value = LoansState.Error(ErrorType.INTERNET)
            is HttpException -> {
                when {
                    (exception.code() == 400) ->
                        _state.value = LoansState.Error(ErrorType.HTTP400)

                    (exception.code() == 401) ->
                        _state.value = LoansState.Error(ErrorType.HTTP401)

                    (exception.code() == 403) ->
                        _state.value = LoansState.Error(ErrorType.HTTP403)

                    (exception.code() == 404) ->
                        _state.value = LoansState.Error(ErrorType.HTTP404)
                }
            }

            else -> _state.value = LoansState.Error(ErrorType.UNKNOWN)
        }
    }

    init {
        getAll()
    }

    fun getAll() {
        _state.value = LoansState.Loading

        viewModelScope.launch(handleError) {
            val token = getTokenUseCase().accessToken
            val loans = getAllLoansUseCase(token)
            _state.value = LoansState.Content(loans)
        }
    }

    fun openLoanDetails(loanId: Long) {
        router.openLoanDetails(loanId)
    }
}