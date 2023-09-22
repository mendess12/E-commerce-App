package com.yusufmendes.sisterslabgraduationproject.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.adapter.ProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentHomeBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        binding.homeRv.setHasFixedSize(true)
        binding.homeRv.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        productAdapter = ProductAdapter(::navigateToDetail)
        binding.homeRv.adapter = productAdapter
        observeLiveData()
    }

    private fun navigateToDetail(product: ProductX) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
        findNavController().navigate(action)
    }

    private fun observeLiveData() {
        viewModel.productLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                productAdapter.updateProductList(it)
            } else {
                Snackbar.make(requireView(), "Liste boş", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}