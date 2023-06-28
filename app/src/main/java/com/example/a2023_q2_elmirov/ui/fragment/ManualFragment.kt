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
import com.example.a2023_q2_elmirov.databinding.FragmentManualBinding
import com.example.a2023_q2_elmirov.domain.entity.ManualStep
import com.example.a2023_q2_elmirov.domain.entity.ManualStep.LOAN
import com.example.a2023_q2_elmirov.domain.entity.ManualStep.LOANS
import com.example.a2023_q2_elmirov.presentation.state.ManualState
import com.example.a2023_q2_elmirov.presentation.state.ManualState.Content
import com.example.a2023_q2_elmirov.presentation.state.ManualState.Initial
import com.example.a2023_q2_elmirov.presentation.viewmodel.ManualViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ManualFragment : Fragment() {

    private var _binding: FragmentManualBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.null_binding)
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ManualViewModel::class.java]
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
        _binding = FragmentManualBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()

        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            bPrev.setOnClickListener {
                viewModel.showLoanManual()
            }

            bNext.setOnClickListener {
                viewModel.showLoanListManual()
            }
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: ManualState) {
        when (state) {

            Initial -> Unit

            is Content -> applyContentState(state.step)
        }
    }

    private fun applyContentState(step: ManualStep) {
        when (step) {
            LOAN -> showLoanManual()

            LOANS -> showUserLoansManual()
        }
    }

    private fun showLoanManual() {
        with(binding) {
            bPrev.isVisible = false
            bNext.isVisible = true

            tvManual.text = getString(R.string.loan_manual)
            ivManual.setImageResource(R.drawable.image_loan_manual)
        }
    }

    private fun showUserLoansManual() {
        with(binding) {
            bPrev.isVisible = true
            bNext.isVisible = false

            tvManual.text = getString(R.string.loans_manual)
            ivManual.setImageResource(R.drawable.image_loans_manual)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}