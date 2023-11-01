package com.yusufmendes.sisterslabgraduationproject.ui.view.bag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.ui.adapter.BagProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentBagBinding
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagBody
import com.yusufmendes.sisterslabgraduationproject.ui.util.showSnackBar
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackBar
import com.yusufmendes.sisterslabgraduationproject.util.storage.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : Fragment(R.layout.fragment_bag) {

    private lateinit var binding: FragmentBagBinding
    private val viewModel: BagViewModel by viewModels()
    private lateinit var bagProductAdapter: BagProductAdapter
    private val args: BagFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBagBinding.bind(view)

        val userId = SharedPrefManager.getInstance(requireActivity()).data.userId

        bagProductAdapter = BagProductAdapter {
            viewModel.deleteProduct(it, userId)
        }
        with(binding.bagRv) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = bagProductAdapter
        }

        binding.bagScreenToolbar.bagToolbarDeleteIv.setOnClickListener {
            viewModel.clearBag(ClearBagBody(userId))
            binding.bagScreenTotalPrice.setText("Total Price: 0.0₺")
        }

        binding.bagScreenBuyButton.setOnClickListener {
            findNavController().navigate(R.id.action_bagFragment_to_paymentFragment)
        }

        viewModel.getBagProducts(userId)

        if (args.payment == 1) {
            viewModel.clearBag(ClearBagBody(userId))
            binding.bagScreenTotalPrice.setText("Total Price: 0.0₺")
            view.showSnackBar("purchase successful")
        }else{
            observeLiveData()
        }

    }

    private fun observeLiveData() {
        with(viewModel) {
            bagLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    it.products?.let { it1 -> bagProductAdapter.updateProductList(it1) }
                    val totalPrice = it.products?.let { it1 -> viewModel.totalPrice(it1) }
                    binding.bagScreenTotalPrice.text = "Total Price: ${totalPrice}₺"
                }.doOnFailure {
                    showError(it)
                }
            }
            deleteLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    view?.showSnackBar("Ürün silindi")
                }.doOnFailure {
                    showError(it)
                }
            }
            clearBagLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    bagProductAdapter.updateProductList(emptyList())
                    view?.showSnackBar("Sepet temizlendi")
                }.doOnFailure {
                    showError(it)
                }
            }
        }
    }

    private fun showError(error: Throwable) {
        showSnackBar(error.message ?: "Unexpected error")
    }
}