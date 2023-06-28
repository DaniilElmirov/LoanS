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
import com.example.a2023_q2_elmirov.databinding.FragmentLoanDetailsBinding
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP400
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP401
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP403
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP404
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INTERNET
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INVALID
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.UNKNOWN
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.domain.entity.LoanStatus
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState.Content
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState.Error
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState.Initial
import com.example.a2023_q2_elmirov.presentation.state.LoanDetailsState.Loading
import com.example.a2023_q2_elmirov.presentation.viewmodel.LoanDetailsViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class LoanDetailsFragment : Fragment() {

    companion object {
        private const val DEFAULT_ID = -1L
        private const val LOAN_ID = "Loan"

        fun newInstance(loanId: Long): LoanDetailsFragment {
            return LoanDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(LOAN_ID, loanId)
                }
            }
        }
    }

    private var _binding: FragmentLoanDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.null_binding)
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoanDetailsViewModel::class.java]
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
        _binding = FragmentLoanDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO посмотреть про хеширование в http client чтобы при пересоздании не ходить в сеть
        getLoan()

        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun getLoan() {
        val loanId = requireArguments().getLong(LOAN_ID, DEFAULT_ID)
        viewModel.getLoanById(loanId)
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: LoanDetailsState) {
        when (state) {
            Initial -> Unit

            Loading -> applyLoadingState()

            is Content -> applyContentState(state.loan)

            is Error -> applyErrorState(state.errorType)
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            progressBar.isVisible = true
            tvError.isVisible = false
            bError.isVisible = false
            getTitle.isVisible = false
            tvGet.isVisible = false
            tvStatus.isVisible = false
            amountTitle.isVisible = false
            tvAmount.isVisible = false
            dateTitle.isVisible = false
            tvDate.isVisible = false
            firstNameTitle.isVisible = false
            tvFirstName.isVisible = false
            lastNameTitle.isVisible = false
            tvLastName.isVisible = false
            percentTitle.isVisible = false
            tvPercent.isVisible = false
            periodTitle.isVisible = false
            tvPeriod.isVisible = false
            phoneNumberTitle.isVisible = false
            tvPhoneNumber.isVisible = false
        }
    }

    private fun applyContentState(loan: Loan) {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")

        setContentVisibility()

        with(binding) {
            when (loan.status) {
                LoanStatus.APPROVED -> {
                    getTitle.isVisible = true
                    tvGet.isVisible = true

                    tvStatus.setTextColor(ColorStateList.valueOf(Color.GREEN))
                }

                LoanStatus.REJECTED -> {
                    getTitle.isVisible = false
                    tvGet.isVisible = false

                    tvStatus.setTextColor(ColorStateList.valueOf(Color.RED))
                }

                LoanStatus.REGISTERED -> {
                    getTitle.isVisible = false
                    tvGet.isVisible = true
                    tvGet.text = getString(R.string.status_registered)

                    tvStatus.setTextColor(ColorStateList.valueOf(Color.BLACK))
                }
            }

            tvStatus.text = loan.status.toString()
            tvAmount.text = loan.amount.toString()
            tvDate.text = loan.date.format(formatter)
            tvFirstName.text = loan.firstName
            tvLastName.text = loan.lastName
            tvPercent.text = loan.percent.toString()
            tvPeriod.text = loan.period.toString()
            tvPhoneNumber.text = loan.phoneNumber
        }
    }

    private fun applyErrorState(errorType: ErrorType) {

        setErrorVisibility()

        when (errorType) {
            INTERNET -> showInternetError()

            HTTP403 -> showLoginAgainError()

            HTTP404 -> showNotFoundError()

            HTTP400, HTTP401, UNKNOWN -> showUnknownError()

            INVALID -> Unit
        }
    }

    private fun setContentVisibility() {
        with(binding) {
            progressBar.isVisible = false
            tvError.isVisible = false
            bError.isVisible = false
            tvStatus.isVisible = true
            amountTitle.isVisible = true
            tvAmount.isVisible = true
            dateTitle.isVisible = true
            tvDate.isVisible = true
            firstNameTitle.isVisible = true
            tvFirstName.isVisible = true
            lastNameTitle.isVisible = true
            tvLastName.isVisible = true
            percentTitle.isVisible = true
            tvPercent.isVisible = true
            periodTitle.isVisible = true
            tvPeriod.isVisible = true
            phoneNumberTitle.isVisible = true
            tvPhoneNumber.isVisible = true
        }
    }

    private fun setErrorVisibility() {
        with(binding) {
            progressBar.isVisible = false
            tvError.isVisible = true
            bError.isVisible = true
            getTitle.isVisible = false
            tvGet.isVisible = false
            tvStatus.isVisible = false
            amountTitle.isVisible = false
            tvAmount.isVisible = false
            dateTitle.isVisible = false
            tvDate.isVisible = false
            firstNameTitle.isVisible = false
            tvFirstName.isVisible = false
            lastNameTitle.isVisible = false
            tvLastName.isVisible = false
            percentTitle.isVisible = false
            tvPercent.isVisible = false
            periodTitle.isVisible = false
            tvPeriod.isVisible = false
            phoneNumberTitle.isVisible = false
            tvPhoneNumber.isVisible = false
        }
    }

    private fun showInternetError() {
        with(binding) {
            tvError.text = getString(R.string.error_internet_text)

            bError.text = getString(R.string.try_again)
            bError.setOnClickListener {
                getLoan()
            }
        }
    }

    private fun showLoginAgainError() {
        with(binding) {
            tvError.text = getString(R.string.error_http403_text)

            bError.text = getString(R.string.login_again)
            bError.setOnClickListener {
                viewModel.loginAgain()
            }
        }
    }

    private fun showNotFoundError() {
        with(binding) {
            tvError.text = getString(R.string.error_http404_loan_text)

            bError.text = getString(R.string.b_ok_text)
            bError.setOnClickListener {
                viewModel.backToLoans()
            }
        }
    }

    private fun showUnknownError() {
        with(binding) {
            tvError.text = getString(R.string.error_unknown_text)

            bError.text = getString(R.string.b_ok_text)
            bError.setOnClickListener {
                viewModel.backToLoans()
            }
        }
    }
}