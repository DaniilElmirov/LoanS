package com.example.a2023_q2_elmirov.ui.fragment

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
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
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState.Content
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState.Error
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState.Initial
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState.Loading
import com.example.a2023_q2_elmirov.presentation.state.ApplyLoanState.Result
import com.example.a2023_q2_elmirov.presentation.viewmodel.ApplyLoanViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ApplyLoanFragment : Fragment() {

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

        setupSeekBar()
        setSeekBarProgress()

        initListeners()

        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupSeekBar() {

        binding.sbAmount.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, userInput: Boolean) {
                binding.sbAmountText.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null)
                    viewModel.setupProgress(seekBar.progress)
            }
        })
    }

    private fun setSeekBarProgress() {
        viewModel.amount.observe(viewLifecycleOwner) {
            binding.sbAmount.progress = it
        }
    }

    private fun initListeners() {
        with(binding) {
            bApplyLoan.setOnClickListener {
                val amount = sbAmountText.text.toString()
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
            Initial -> Unit

            is Loading -> applyLoadingState()

            is Content -> applyContentState(state.loanConditions) //TODO переделать разметку

            is Result -> applyResultState(state.status) //TODO добавить картинки в зависимости от статуса

            is Error -> applyErrorState(state.errorType)
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

            amount.isVisible = false
            sbAmountText.isVisible = false
            sbAmount.isVisible = false

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

            amount.isVisible = true
            sbAmountText.isVisible = true
            sbAmount.isVisible = true

            tilFirstName.isVisible = true
            tilLastName.isVisible = true
            tilPhoneNumber.isVisible = true

            bApplyLoan.isVisible = true

            progressBar.isVisible = false

            sbAmount.max = loanConditions.maxAmount
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

            amount.isVisible = false
            sbAmountText.isVisible = false
            sbAmount.isVisible = false

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

            amount.isVisible = false
            sbAmountText.isVisible = false
            sbAmount.isVisible = false

            tilFirstName.isVisible = false
            tilLastName.isVisible = false
            tilPhoneNumber.isVisible = false

            bApplyLoan.isVisible = false

            progressBar.isVisible = false

            when (errorType) {
                INTERNET -> tvError.text = getString(R.string.error_internet_text)

                HTTP400 -> Unit

                HTTP401 -> Unit

                HTTP403 -> tvError.text = getString(R.string.error_http403_text)

                HTTP404 -> Unit

                UNKNOWN -> tvError.text = getString(R.string.error_unknown_text)

                INVALID -> showInvalidError()
            }
        }
    }

    private fun showInvalidError() {
        with(binding) {
            tvError.isVisible = false

            tvConditionsTitle.isVisible = true

            tvMaxAmountTitle.isVisible = true
            tvMaxAmount.isVisible = true

            tvPercentTitle.isVisible = true
            tvPercent.isVisible = true

            tvPeriodTitle.isVisible = true
            tvPeriod.isVisible = true

            tvEnterDataTitle.isVisible = true

            amount.isVisible = true
            sbAmountText.isVisible = true
            sbAmount.isVisible = true

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
        }
    }
}