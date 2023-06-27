package com.example.a2023_q2_elmirov.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.a2023_q2_elmirov.domain.entity.ManualStep
import com.example.a2023_q2_elmirov.presentation.state.ManualState
import javax.inject.Inject

class ManualViewModel @Inject constructor(

) : ViewModel() {

    private val _state = MutableLiveData<ManualState>(ManualState.Initial)
    val state: LiveData<ManualState> = _state

    init {
        showLoanManual()
    }

    fun showLoanManual() {
        _state.value = ManualState.Content(ManualStep.LOAN)
    }

    fun showLoanListManual() {
        _state.value = ManualState.Content(ManualStep.LOANS)
    }
}