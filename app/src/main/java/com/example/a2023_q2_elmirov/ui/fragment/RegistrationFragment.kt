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
import com.example.a2023_q2_elmirov.databinding.FragmentRegistrationBinding
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP400
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP401
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP403
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP404
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INTERNET
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INVALID
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.UNKNOWN
import com.example.a2023_q2_elmirov.presentation.state.RegistrationState
import com.example.a2023_q2_elmirov.presentation.viewmodel.RegistrationViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.null_binding)
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RegistrationViewModel::class.java]
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
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
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
        binding.bSignUp.setOnClickListener {
            val auth = getAuth()
            viewModel.registration(auth)
        }
    }

    private fun getAuth(): Auth {
        with(binding) {
            val name = etName.text.toString()
            val password = etPassword.text.toString()

            return Auth(name, password)
        }
    }

    private fun initObservers() {
        viewModel.state.observe(viewLifecycleOwner, ::applyState)
    }

    private fun applyState(state: RegistrationState) {
        when (state) {
            RegistrationState.Initial -> Unit

            is RegistrationState.Loading -> applyLoadingState()

            is RegistrationState.Error -> applyErrorState(state.errorType)
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            tvError.isVisible = false

            tilName.isVisible = false
            tilPassword.isVisible = false

            bSignUp.isVisible = false

            progressBar.isVisible = true
        }
    }

    private fun applyErrorState(errorType: ErrorType) {
        with(binding) {
            tvError.isVisible = true

            tilName.isVisible = true
            tilPassword.isVisible = true

            bSignUp.isVisible = true

            progressBar.isVisible = false
        }

        when (errorType) {
            INTERNET -> showInternetError()

            HTTP400 -> showUserExistError()

            HTTP401, HTTP403, HTTP404, UNKNOWN  -> showUnknownError()

            INVALID -> showInvalidInputError()
        }
    }
    //TODO спросить что подразумевают ошибки

    private fun showInternetError() {
        binding.tvError.text = getString(R.string.error_internet_text)
    }

    private fun showUserExistError() {
        binding.tvError.text = String.format(getString(R.string.error_http400_text), getAuth().name)
    }

    private fun showUnknownError() {
        binding.tvError.text = getString(R.string.error_unknown_text)
    }

    private fun showInvalidInputError() {
        with(binding) {
            tvError.isVisible = false

            etName.hint = getString(R.string.error_invalid_input_text)
            etName.setHintTextColor(ColorStateList.valueOf(Color.RED))

            etPassword.hint = getString(R.string.error_invalid_input_text)
            etPassword.setHintTextColor(ColorStateList.valueOf(Color.RED))
        }
    }
}