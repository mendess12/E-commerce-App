package com.yusufmendes.sisterslabgraduationproject.ui.view.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentProfileBinding
import com.yusufmendes.sisterslabgraduationproject.ui.util.showSnackBar
import com.yusufmendes.sisterslabgraduationproject.util.storage.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)
        val userId = SharedPrefManager.getInstance(requireActivity()).data.userId
        viewModel.getUser(userId)
        observeLiveData()

        binding.profileSignOutButton.setOnClickListener {
            SharedPrefManager.getInstance(requireActivity()).clear()
            findNavController().navigate(R.id.auth_nav_graph)
        }
    }

    private fun observeLiveData() {
        viewModel.getUserLiveData.observe(viewLifecycleOwner) {
            it.doOnSuccess {
                val email = it.user.email
                val name = it.user.name
                val phone = it.user.phone
                val address = it.user.address
                with(binding) {
                    profileUserEmail.text = email
                    profileUserName.setText(name)
                    profileUserPhone.setText(phone)
                    profileUserAddress.setText(address)
                }
            }.doOnFailure {
                showError(it)
            }
        }
    }

    private fun showError(error: Throwable) {
        showSnackBar(error.message ?: "Unexpected error")
    }
}