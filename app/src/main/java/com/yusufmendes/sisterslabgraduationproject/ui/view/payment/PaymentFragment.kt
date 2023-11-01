package com.yusufmendes.sisterslabgraduationproject.ui.view.payment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private lateinit var binding: FragmentPaymentBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPaymentBinding.bind(view)

        binding.paymentScreenToolbar.toolbarTitle.setText(R.string.payment)
        binding.paymentScreenToolbar.backToolBar.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}