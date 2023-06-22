package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanRequest
import com.example.a2023_q2_elmirov.domain.usecase.CreateLoanUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetLoanConditionsUseCase
import com.example.a2023_q2_elmirov.domain.usecase.GetTokenUseCase
import com.example.a2023_q2_elmirov.presentation.router.ApplyLoanRouter
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class ApplyLoanViewModel @Inject constructor(
    private val router: ApplyLoanRouter,
    private val getLoanConditionsUseCase: GetLoanConditionsUseCase,
    private val getTokenUseCase: GetTokenUseCase,
    private val createLoanUseCase: CreateLoanUseCase,
) : ViewModel() {

    companion object {
        private const val MIN_LOAN_AMOUNT = 1
    }

    private val _state = MutableLiveData<ApplyLoanState>(ApplyLoanState.Initial)
    val state: LiveData<ApplyLoanState> = _state

    private val handleError = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is UnknownHostException -> _state.value = ApplyLoanState.Error(ErrorType.INTERNET)

            is NullPointerException -> _state.value = ApplyLoanState.Error(ErrorType.HTTP403)

            else -> _state.value = ApplyLoanState.Error(ErrorType.UNKNOWN)
        }
    }

    private lateinit var loanConditions: LoanConditions

    init {
        getLoanConditions()
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
            LoanRequest(amount.toLong(), firstName, lastName, percent, period, phoneNumber)

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
            loanRequest.amount > loanConditions.maxAmount ||
            loanRequest.amount < MIN_LOAN_AMOUNT ||
            loanRequest.firstName.isBlank() ||
            loanRequest.lastName.isBlank() ||
            loanRequest.phoneNumber.isBlank()
        )
            result = false

        return result
    }
}