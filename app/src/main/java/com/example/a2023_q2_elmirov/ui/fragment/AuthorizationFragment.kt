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
import com.example.a2023_q2_elmirov.databinding.FragmentAuthorizationBinding
import com.example.a2023_q2_elmirov.domain.entity.Auth
import com.example.a2023_q2_elmirov.domain.entity.ErrorType
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP400
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP401
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP403
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.HTTP404
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INTERNET
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.INVALID
import com.example.a2023_q2_elmirov.domain.entity.ErrorType.UNKNOWN
import com.example.a2023_q2_elmirov.presentation.state.AuthorizationState
import com.example.a2023_q2_elmirov.presentation.viewmodel.AuthorizationViewModel
import com.example.a2023_q2_elmirov.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class AuthorizationFragment : Fragment() {

    private var _binding: FragmentAuthorizationBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            getString(R.string.null_binding)
        }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthorizationViewModel::class.java]
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
        _binding = FragmentAuthorizationBinding.inflate(inflater, container, false)
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
        binding.bSignIn.setOnClickListener {
            val auth = getAuth()
            viewModel.authorization(auth)
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

    private fun applyState(state: AuthorizationState) {
        when (state) {
            AuthorizationState.Initial -> Unit

            is AuthorizationState.Loading -> applyLoadingState()

            is AuthorizationState.Error -> applyErrorState(state.errorType)
        }
    }

    private fun applyLoadingState() {
        with(binding) {
            tvError.isVisible = false

            tilName.isVisible = false
            tilPassword.isVisible = false

            bSignIn.isVisible = false

            progressBar.isVisible = true
        }
    }

    private fun applyErrorState(errorType: ErrorType) {
        with(binding) {
            tvError.isVisible = true

            tilName.isVisible = true
            tilPassword.isVisible = true

            bSignIn.isVisible = true

            progressBar.isVisible = false
        }

        when (errorType) {
            INTERNET -> showInternetError()

            HTTP400 -> showUnknownError()

            HTTP401 -> showUnknownError()

            HTTP403 -> showUnknownError()

            HTTP404 -> showNotFoundError()

            UNKNOWN -> showUnknownError()

            INVALID -> showInvalidInputError()
        }
    }

    private fun showInternetError() {
        binding.tvError.text = getString(R.string.error_internet_text)
    }

    private fun showNotFoundError() {
        binding.tvError.text = getString(R.string.error_http404_text)
    }

    private fun showUnknownError() {
        binding.tvError.text = getString(R.string.error_unknown_text)
    }

    private fun showInvalidInputError() {
        binding.etName.hint = getString(R.string.error_invalid_input_text)
        binding.etName.setHintTextColor(ColorStateList.valueOf(Color.RED))

        binding.etPassword.hint = getString(R.string.error_invalid_input_text)
        binding.etPassword.setHintTextColor(ColorStateList.valueOf(Color.RED))
    }
}