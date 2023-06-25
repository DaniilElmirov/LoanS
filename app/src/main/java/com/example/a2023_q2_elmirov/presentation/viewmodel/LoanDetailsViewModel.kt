package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.usecase.GetLoanByIdUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class LoanDetailsViewModel @Inject constructor(
    private val getLoanByIdUseCase: GetLoanByIdUseCase,
    private val getTokenUseCase: GetTokenUseCase,
) : ViewModel() {

    private val _state = MutableLiveData<LoanDetailsState>(LoanDetailsState.Initial)
    val state: LiveData<LoanDetailsState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException -> _state.value = LoanDetailsState.Error(ErrorType.INTERNET)
            is HttpException -> {
                when {
                    (exception.code() == 400) ->
                        _state.value = LoanDetailsState.Error(ErrorType.HTTP400)

                    (exception.code() == 401) ->
                        _state.value = LoanDetailsState.Error(ErrorType.HTTP401)

                    (exception.code() == 403) ->
                        _state.value = LoanDetailsState.Error(ErrorType.HTTP403)

                    (exception.code() == 404) ->
                        _state.value = LoanDetailsState.Error(ErrorType.HTTP404)
                }
            }

            else -> _state.value = LoanDetailsState.Error(ErrorType.UNKNOWN)
        }
    }

    fun getLoanById(loanId: Long) {
        _state.value = LoanDetailsState.Loading

        val token = getTokenUseCase().accessToken

        viewModelScope.launch(handleError) {
            val loan = getLoanByIdUseCase(token, loanId)
            _state.value = LoanDetailsState.Content(loan)
        }
    }
}