package com.yusufmendes.sisterslabgraduationproject.view.bag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.adapter.BagProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentBagBinding
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagRequest
import com.yusufmendes.sisterslabgraduationproject.model.DeleteCartRequest
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : Fragment(R.layout.fragment_bag) {

    private lateinit var binding: FragmentBagBinding
    private val viewModel: BagViewModel by viewModels()
    private lateinit var bagProductAdapter: BagProductAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBagBinding.bind(view)

        binding.bagRv.setHasFixedSize(true)
        binding.bagRv.layoutManager = LinearLayoutManager(requireContext())
        bagProductAdapter = BagProductAdapter {
            viewModel.deleteProduct(DeleteCartRequest(it))
        }
        binding.bagScreenToolbar.bagToolbarDeleteIv.setOnClickListener {
            viewModel.clearBag(ClearBagRequest("b3sa6dj721312ssadas21d"))
        }
        binding.bagRv.adapter = bagProductAdapter
        viewModel.getBagProducts()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.bagLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                bagProductAdapter.updateProductList(it)
            } else {
                view?.showSnackbar("Sepet boş")
            }
        }
        viewModel.deleteLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                view?.showSnackbar("Ürün silindi")
            } else {
                view?.showSnackbar("Ürün silinemedi")
            }
        }
        viewModel.clearBagLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                view?.showSnackbar("Sepet temizlendi")
            } else {
                view?.showSnackbar("Sepet silinemedi")
            }
        }
    }
}