package com.example.a2023_q2_elmirov.ui.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.R
import com.example.a2023_q2_elmirov.databinding.FragmentApplyLoanBinding
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP400
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP401
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP403
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP404
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INTERNET
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INVALID
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.UNKNOWN
import com.example.a2023_q2_elmirov.domain.entity.LoanConditions
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.APPROVED
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.REGISTERED
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus.REJECTED
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState
import com.example.a2023_q2_elmirov.presentation.viewmodel.ApplyLoanViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ApplyLoanFragment : Fragment() {

    companion object {
        private const val ZERO_LOAN_AMOUNT = "0"
    }

    private var _binding: FragmentApplyLoanBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ApplyLoanViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as LoansApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentApplyLoanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initListeners() {
        with(binding) {
            bApplyLoan.setOnClickListener {
                val amount =
                    if (etAmount.text.isNullOrBlank()) ZERO_LOAN_AMOUNT else etAmount.text.toString() //TODO переделать
                val firstName = etFirstName.text.toString()
                val lastName = etLastName.text.toString()
                val phoneNumber = etPhoneNumber.text.toString()

                viewModel.createLoan(amount, firstName, lastName, phoneNumber)
            }

            bOk.setOnClickListener {
                viewModel.backToUserOptions()
            }
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: ApplyLoanState) {
        when (state) {
            ApplyLoanState.Initial -> Unit

            is ApplyLoanState.Loading -> applyLoadingState()

            is ApplyLoanState.Content -> applyContentState(state.loanConditions) //TODO переделать разметку

            is ApplyLoanState.Result -> applyResultState(state.status) //TODO добавить картинки в зависимости от статуса

            is ApplyLoanState.Error -> applyErrorState(state.errorType)
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            tvError.isVisible = false

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = false

            tvMaxAmountTitle.isVisible = false
            tvMaxAmount.isVisible = false

            tvPercentTitle.isVisible = false
            tvPercent.isVisible = false

            tvPeriodTitle.isVisible = false
            tvPeriod.isVisible = false

            tvEnterDataTitle.isVisible = false

            tilAmount.isVisible = false
            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = true
        }
    }

    private fun applyContentState(loanConditions: LoanConditions) {
        with(binding) {
            tvError.isVisible = false

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = true

            tvMaxAmountTitle.isVisible = true
            tvMaxAmount.isVisible = true
            tvMaxAmount.text = loanConditions.maxAmount.toString()

            tvPercentTitle.isVisible = true
            tvPercent.isVisible = true
            tvPercent.text = loanConditions.percent.toString()

            tvPeriodTitle.isVisible = true
            tvPeriod.isVisible = true
            tvPeriod.text = loanConditions.period.toString()

            tvEnterDataTitle.isVisible = true

            tilAmount.isVisible = true
            tilFirstName.isVisible = true
            tilLastName.isVisible = true
            tilPhoneNumber.isVisible = true

            bApplyLoan.isVisible = true

            progressBar.isVisible = false
        }
    }

    private fun applyResultState(status: LoanStatus) {
        with(binding) {
            tvError.isVisible = false

            tvResult.isVisible = true
            bOk.isVisible = true

            tvConditionsTitle.isVisible = false

            tvMaxAmountTitle.isVisible = false
            tvMaxAmount.isVisible = false

            tvPercentTitle.isVisible = false
            tvPercent.isVisible = false

            tvPeriodTitle.isVisible = false
            tvPeriod.isVisible = false

            tvEnterDataTitle.isVisible = false

            tilAmount.isVisible = false
            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = false
        }

        when (status) {
            APPROVED -> showApprovedStatus()

            REGISTERED -> showRegisteredStatus()

            REJECTED -> showRejectedStatus()
        }
    }

    private fun showApprovedStatus() {
        with(binding) {
            tvResult.setTextColor(ColorStateList.valueOf(Color.GREEN))
            tvResult.text = getString(R.string.status_approved)

            bOk.isVisible = true
        }
    }

    private fun showRegisteredStatus() {
        with(binding) {
            tvResult.text = getString(R.string.status_registered)
            bOk.isVisible = true
        }
    }

    private fun showRejectedStatus() {
        with(binding) {
            tvResult.setTextColor(ColorStateList.valueOf(Color.RED))
            tvResult.text = getString(R.string.status_rejected)

            bOk.isVisible = true
        }
    }

    private fun applyErrorState(errorType: ErrorType) {
        when (errorType) {
            INTERNET -> showInternetError()

            HTTP400 -> Unit

            HTTP401 -> Unit

            HTTP403 -> showLoginAgainError()

            HTTP404 -> Unit

            UNKNOWN -> showUnknownError()

            INVALID -> showInvalidError()
        }
    }

    private fun showInternetError() {
        with(binding) {
            tvError.isVisible = true

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = false

            tvMaxAmountTitle.isVisible = false
            tvMaxAmount.isVisible = false

            tvPercentTitle.isVisible = false
            tvPercent.isVisible = false

            tvPeriodTitle.isVisible = false
            tvPeriod.isVisible = false

            tvEnterDataTitle.isVisible = false

            tilAmount.isVisible = false
            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = false

            tvError.text = getString(R.string.error_internet_text)
        }
    }

    private fun showLoginAgainError() {
        with(binding) {
            tvError.isVisible = true

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = false

            tvMaxAmountTitle.isVisible = false
            tvMaxAmount.isVisible = false

            tvPercentTitle.isVisible = false
            tvPercent.isVisible = false

            tvPeriodTitle.isVisible = false
            tvPeriod.isVisible = false

            tvEnterDataTitle.isVisible = false

            tilAmount.isVisible = false
            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = false

            tvError.text = getString(R.string.error_http403_text)
        }
    }

    private fun showUnknownError() {
        with(binding) {
            tvError.isVisible = true

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = false

            tvMaxAmountTitle.isVisible = false
            tvMaxAmount.isVisible = false

            tvPercentTitle.isVisible = false
            tvPercent.isVisible = false

            tvPeriodTitle.isVisible = false
            tvPeriod.isVisible = false

            tvEnterDataTitle.isVisible = false

            tilAmount.isVisible = false
            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = false

            tvError.text = getString(R.string.error_unknown_text)
        }
    }

    private fun showInvalidError() {
        with(binding) {
            tvError.isVisible = false

            tvResult.isVisible = false
            bOk.isVisible = false

            tvConditionsTitle.isVisible = true

            tvMaxAmountTitle.isVisible = true
            tvMaxAmount.isVisible = true

            tvPercentTitle.isVisible = true
            tvPercent.isVisible = true

            tvPeriodTitle.isVisible = true
            tvPeriod.isVisible = true

            tvEnterDataTitle.isVisible = true

            tilAmount.isVisible = true
            tilAmount.hintTextColor = ColorStateList.valueOf(Color.RED)
            tilAmount.hint =
                String.format(getString(R.string.error_invalid_input_amount), tvMaxAmount.text)

            tilFirstName.isVisible = true
            etFirstName.setHintTextColor(ColorStateList.valueOf(Color.RED))
            etFirstName.hint = getString(R.string.error_invalid_input_text)

            tilLastName.isVisible = true
            etLastName.setHintTextColor(ColorStateList.valueOf(Color.RED))
            etLastName.hint = getString(R.string.error_invalid_input_text)

            tilPhoneNumber.isVisible = true
            etPhoneNumber.setHintTextColor(ColorStateList.valueOf(Color.RED))
            etPhoneNumber.hint = getString(R.string.error_invalid_input_text)

            bApplyLoan.isVisible = true

            progressBar.isVisible = false
        }
    }
}