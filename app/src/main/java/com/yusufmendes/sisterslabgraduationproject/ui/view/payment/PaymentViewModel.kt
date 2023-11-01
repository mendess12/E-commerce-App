package com.yusufmendes.sisterslabgraduationproject.ui.view.payment

import androidx.lifecycle.ViewModel
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentPaymentBinding
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(): ViewModel() {

    fun checkPaymentData(
        binding: FragmentPaymentBinding,
        name: String,
        cartNumber: String,
        month: String,
        year: String,
        cvv: String,
        address: String
    ): Boolean {

        return when {
            name.isEmpty() -> {
                binding.paymentScreenNameEt.error = "Name can't be empty"
                binding.paymentScreenNameEt.requestFocus()
                false
            }

            cartNumber.isEmpty() -> {
                binding.paymentScreenCartNumberEt.error = "Cart Number can't be empty"
                binding.paymentScreenCartNumberEt.requestFocus()
                false
            }

            cartNumber.length < 12 -> {
                binding.paymentScreenCartNumberEt.error = "12 char cart number required"
                binding.paymentScreenCartNumberEt.requestFocus()
                false
            }

            month.isEmpty() -> {
                binding.paymentScreenMonthEt.error = "Month can't be empty"
                binding.paymentScreenMonthEt.requestFocus()
                false
            }

            year.isEmpty() -> {
                binding.paymentScreenYearEt.error = "Year can't be empty"
                binding.paymentScreenYearEt.requestFocus()
                false
            }

            year.length < 4 -> {
                binding.paymentScreenYearEt.error = "4 char year required"
                binding.paymentScreenYearEt.requestFocus()
                false
            }

            cvv.isEmpty() -> {
                binding.paymentScreenCvvEt.error = "Cvv can't be empty"
                binding.paymentScreenCvvEt.requestFocus()
                false
            }

            cvv.length < 3 -> {
                binding.paymentScreenCvvEt.error = "3 char cvv required"
                binding.paymentScreenCvvEt.requestFocus()
                false
            }

            address.isEmpty() -> {
                binding.paymentScreenAddressEt.error = "Address can't be empty"
                binding.paymentScreenAddressEt.requestFocus()
                false
            }

            else -> true
        }

    }

}