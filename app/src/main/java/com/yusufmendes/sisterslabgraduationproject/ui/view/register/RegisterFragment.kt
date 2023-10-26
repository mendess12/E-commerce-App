package com.yusufmendes.sisterslabgraduationproject.ui.view.register

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentRegisterBinding
import com.yusufmendes.sisterslabgraduationproject.model.RegisterBody
import com.yusufmendes.sisterslabgraduationproject.ui.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment(R.layout.fragment_register) {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        binding.registerScreenToolbar.toolbarTitle.visibility = View.GONE

        with(binding) {
            registerScreenToolbar.backToolBar.setOnClickListener {
                navigateToLogin()
            }
            registerScreenLoginTv.setOnClickListener {
                navigateToLogin()
            }
            registerScreenRegisterButton.setOnClickListener {
                val email = binding.registerScreenEmailEt.text.toString()
                val password = binding.registerScreenPasswordEt.text.toString()
                val name = binding.registerScreenNamedEt.text.toString()
                val phone = binding.registerScreenPhonedEt.text.toString()
                val address = binding.registerScreenAddressdEt.text.toString()
                if (isEligibleToLogin(email, password, name, phone, address)) {
                    viewModel.register(RegisterBody(address, email, name, password, phone))
                }
            }
        }
        observeLiveData()
    }

    private fun navigateToLogin() {
        findNavController().popBackStack()
    }

    private fun observeLiveData() {
        viewModel.registerLiveData.observe(viewLifecycleOwner) {
            it.doOnSuccess {
                findNavController().popBackStack()
            }.doOnFailure {
                showError(it)
            }
        }
    }

    private fun isEligibleToLogin(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ): Boolean {
        return when {
            email.isEmpty() -> {
                binding.registerScreenEmailEt.error = "Email can't be empty"
                binding.registerScreenEmailEt.requestFocus()
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.registerScreenEmailEt.error = "valid email required"
                binding.registerScreenEmailEt.requestFocus()
                false
            }

            password.isEmpty() -> {
                binding.registerScreenPasswordEt.error = "Password can't be empty"
                binding.registerScreenPasswordEt.requestFocus()
                false
            }

            password.length < 6 -> {
                binding.registerScreenPasswordEt.error = "6 char password required"
                binding.registerScreenPasswordEt.requestFocus()
                false
            }

            name.isEmpty() -> {
                binding.registerScreenNamedEt.error = "Name can't be empty"
                binding.registerScreenNamedEt.requestFocus()
                false
            }

            phone.isEmpty() -> {
                binding.registerScreenPhonedEt.error = "Phone can't be empty"
                binding.registerScreenPhonedEt.requestFocus()
                false
            }

            phone.length < 11 -> {
                binding.registerScreenPhonedEt.error = "11 char password required"
                binding.registerScreenPhonedEt.requestFocus()
                false
            }

            address.isEmpty() -> {
                binding.registerScreenAddressdEt.error = "Address can't be empty"
                binding.registerScreenAddressdEt.requestFocus()
                false
            }

            else -> true
        }
    }

    private fun showError(error: Throwable) {
        showSnackBar(error.message ?: "Unexpected error")
    }
}