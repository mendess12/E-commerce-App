package com.yusufmendes.sisterslabgraduationproject.ui.view.login

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentLoginBinding
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import com.yusufmendes.sisterslabgraduationproject.ui.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            loginScreenLoginButton.setOnClickListener {
                val email = binding.loginScreenEmailEt.text.toString()
                val password = binding.loginScreenPasswordEt.text.toString()
                if (isEligibleToLogin(email, password)) {
                    viewModel.login(LoginBody(email, password))
                }
            }
            loginScreenRegisterTv.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.loginLiveData.observe(viewLifecycleOwner) {
            it.doOnSuccess {
                findNavController().navigate(R.id.action_loginFragment_to_nav_graph)
            }.doOnFailure {
                showError(it)
            }
        }
    }

    private fun isEligibleToLogin(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.loginScreenEmailEt.error = "Email can't be empty"
                binding.loginScreenEmailEt.requestFocus()
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.loginScreenEmailEt.error = "valid email required"
                binding.loginScreenEmailEt.requestFocus()
                false
            }

            password.isEmpty() -> {
                binding.loginScreenPasswordEt.error = "Password can't be empty"
                binding.loginScreenPasswordEt.requestFocus()
                false
            }

            password.length < 6 -> {
                binding.loginScreenPasswordEt.error = "6 char password required"
                binding.loginScreenPasswordEt.requestFocus()
                false
            }

            else -> true
        }
    }

    private fun showError(error: Throwable) {
        showSnackBar(error.message ?: "Unexpected error")
    }
}