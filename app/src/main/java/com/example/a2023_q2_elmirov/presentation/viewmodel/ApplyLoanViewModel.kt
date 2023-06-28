package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.usecase.CreateLoanUseCase
import com.example.a2023_q2_elmirov.domain.usecase.DeleteTokenUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetLoanConditionsUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.ApplyLoanRouter
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ApplyLoanViewModel @Inject constructor(
    private val router: ApplyLoanRouter,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val createLoanUseCase: CreateLoanUseCase,
) : ViewModel() {

    companion object {
        private const val MIN_LOAN_AMOUNT = 1
    }

    private val _state = MutableLiveData<ApplyLoanState>(ApplyLoanState.Initial)
    val state: LiveData<ApplyLoanState> = _state

    private val _amount = MutableLiveData(MIN_LOAN_AMOUNT)
    val amount: LiveData<Int> = _amount

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException,
            is ConnectException,
            is NoRouteToHostException,
            is SocketTimeoutException,
            -> _state.value = ApplyLoanState.Error(ErrorType.INTERNET)

            is HttpException -> {
                when {
                    //TODO спросить как правильно сбросить state
                    //Надо ли пытаться поймать код ошибки из body по коду?
                    (exception.code() == 400) ->
                        _state.value = ApplyLoanState.Error(ErrorType.HTTP400)

                    (exception.code() == 401) ->
                        _state.value = ApplyLoanState.Error(ErrorType.HTTP401)

                    (exception.code() == 403) ->
                        _state.value = ApplyLoanState.Error(ErrorType.HTTP403)

                    (exception.code() == 404) ->
                        _state.value = ApplyLoanState.Error(ErrorType.HTTP404)
                }
            }

            else -> _state.value = ApplyLoanState.Error(ErrorType.UNKNOWN)
        }
    }

    private lateinit var loanConditions: LoanConditions

    init {
        getLoanConditions()
    }

    fun setupProgress(progress: Int) {
        _amount.value = progress
    }

    private fun getLoanConditions() {
        val token = getTokenUseCase().accessToken

        viewModelScope.launch(handleError) {
            _state.value = ApplyLoanState.Loading

            loanConditions = getLoanConditionsUseCase(token)

            _state.value = ApplyLoanState.Content(loanConditions)
        }
    }

    fun backToUserOptions() {
        router.backToUserOptions()
    }

    fun createLoan(amount: String, firstName: String, lastName: String, phoneNumber: String) {
        _state.value = ApplyLoanState.Loading

        val token = getTokenUseCase().accessToken

        val percent = loanConditions.percent
        val period = loanConditions.period

        val loanRequest =
            LoanRequest(amount.toInt(), firstName, lastName, percent, period, phoneNumber)

        if (!validateInput(loanRequest))
            _state.value = ApplyLoanState.Error(ErrorType.INVALID)
        else {
            viewModelScope.launch(handleError) {
                val status = createLoanUseCase(token, loanRequest).status

                _state.value = ApplyLoanState.Result(status)
            }
        }
    }

    //TODO переделать через live data ошибку ввода
    private fun validateInput(loanRequest: LoanRequest): Boolean {
        var result = true

        if (
            loanRequest.firstName.isBlank() ||
            loanRequest.lastName.isBlank() ||
            loanRequest.phoneNumber.isBlank()
        )
            result = false

        return result
    }

    fun resetInternet() {
        try {
            _state.value = ApplyLoanState.Content(loanConditions)
        } catch (e: UninitializedPropertyAccessException) {
            getLoanConditions()
        }
    }

    fun loginAgain() {
        deleteTokenUseCase()
        router.backToEntry()
    }
}