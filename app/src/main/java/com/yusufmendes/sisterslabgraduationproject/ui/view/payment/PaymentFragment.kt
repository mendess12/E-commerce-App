package com.yusufmendes.sisterslabgraduationproject.ui.view.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        binding.paymentScreenToolbar.toolbarTitle.setText(R.string.payment)
        binding.paymentScreenToolbar.backToolBar.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.paymentScreenPayNowButton.setOnClickListener {
            val name = binding.paymentScreenNameEt.text?.trim().toString()
            val cartNumber = binding.paymentScreenCartNumberEt.text?.trim().toString()
            val month = binding.paymentScreenMonthEt.text?.trim().toString()
            val year = binding.paymentScreenYearEt.text?.trim().toString()
            val cvv = binding.paymentScreenCvvEt.text?.trim().toString()
            val address = binding.paymentScreenAddressEt.text?.trim().toString()

            if (viewModel.checkPaymentData(binding, name, cartNumber, month, year, cvv, address)) {
                val action = PaymentFragmentDirections.actionPaymentFragmentToBagFragment(1)
                findNavController().navigate(action)
            }
        }
    }

}