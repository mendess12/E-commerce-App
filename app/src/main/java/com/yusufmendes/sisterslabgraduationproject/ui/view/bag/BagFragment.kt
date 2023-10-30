package com.yusufmendes.sisterslabgraduationproject.ui.view.bag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
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
        }

        viewModel.getBagProducts(userId)
        observeLiveData()
    }

    private fun observeLiveData() {
        with(viewModel) {
            bagLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    it.products?.let { it1 -> bagProductAdapter.updateProductList(it1) }
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