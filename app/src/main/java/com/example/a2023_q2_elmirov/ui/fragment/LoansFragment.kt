package com.example.a2023_q2_elmirov.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a2023_q2_elmirov.LoansApplication
import com.example.a2023_q2_elmirov.R
import com.example.a2023_q2_elmirov.databinding.FragmentLoansBinding
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP400
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP401
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP403
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP404
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INTERNET
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INVALID
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.UNKNOWN
import com.example.a2023_q2_elmirov.domain.entity.Loan
import com.example.a2023_q2_elmirov.presentation.state.LoansState
import com.example.a2023_q2_elmirov.presentation.state.LoansState.Content
import com.example.a2023_q2_elmirov.presentation.state.LoansState.Error
import com.example.a2023_q2_elmirov.presentation.state.LoansState.Initial
import com.example.a2023_q2_elmirov.presentation.state.LoansState.Loading
import com.example.a2023_q2_elmirov.presentation.viewmodel.LoansViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import com.example.a2023_q2_elmirov.ui.recyclerview.LoanListAdapter
import javax.inject.Inject

class LoansFragment : Fragment() {

    private var _binding: FragmentLoansBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.null_binding)
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LoansViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as LoansApplication).component
    }

    private var loanAdapter: LoanListAdapter? = null

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loanAdapter = LoanListAdapter {
            viewModel.openLoanDetails(it)
        }

        initListeners()

        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding?.recyclerView?.adapter = null
        _binding = null
    }

    private fun initListeners() {
        binding.bUpdate.setOnClickListener {
            viewModel.getAll()
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: LoansState) {
        when (state) {
            Initial -> Unit

            Loading -> applyLoadingState()

            is Content -> applyContentState(state.loans)

            is Error -> applyErrorState(state.errorType)
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            recyclerView.isVisible = false
            bUpdate.isVisible = false
            progressBar.isVisible = true
            tvError.isVisible = false
            tvNoLoans.isVisible = false
        }
    }

    private fun applyContentState(loans: List<Loan>) {
        loanAdapter?.submitList(loans)

        if (loanAdapter?.itemCount == 0) {
            binding.tvNoLoans.isVisible = true
        }

        with(binding) {
            recyclerView.isVisible = true
            recyclerView.adapter = loanAdapter

            bUpdate.isVisible = true
            progressBar.isVisible = false
            tvError.isVisible = false
        }
    }

    private fun applyErrorState(errorType: ErrorType) { //TODO нужна кнопка возврата на экран после показа ошибки
        with(binding) {
            recyclerView.isVisible = false
            bUpdate.isVisible = false
            progressBar.isVisible = false
            tvError.isVisible = true
            tvNoLoans.isVisible = false
        }

        when (errorType) {
            INTERNET -> showInternetError()

            HTTP403 -> showLoginAgainError()

            HTTP400, HTTP401, HTTP404, UNKNOWN -> showUnknownError()

            INVALID -> Unit
        }
    }

    private fun showInternetError() {
        binding.tvError.text = getString(R.string.error_internet_text)
    }

    private fun showLoginAgainError() {
        binding.tvError.text = getString(R.string.error_http403_text)
    }

    private fun showUnknownError() {
        binding.tvError.text = getString(R.string.error_unknown_text)
    }
}