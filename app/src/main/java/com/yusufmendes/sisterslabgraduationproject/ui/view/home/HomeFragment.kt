package com.yusufmendes.sisterslabgraduationproject.ui.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.ui.adapter.CategoryNameAdapter
import com.yusufmendes.sisterslabgraduationproject.ui.adapter.ProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentHomeBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.ui.util.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var productAdapter: ProductAdapter
    private lateinit var categoryNameAdapter: CategoryNameAdapter
    private val viewModel: HomeFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        productAdapter = ProductAdapter(::navigateToDetail)
        with(binding) {
            homeRv.setHasFixedSize(true)
            homeRv.layoutManager =
                GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            homeRv.adapter = productAdapter
        }

        categoryNameAdapter = CategoryNameAdapter(::getCategoryName)
        with(binding) {
            categoryRv.setHasFixedSize(true)
            categoryRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            categoryRv.adapter = categoryNameAdapter
        }

        viewModel.getCategoryName()
        search()
        observeLiveData()
    }

    private fun getCategoryName(categoryName: String) {
        viewModel.getCategory(categoryName)
    }

    private fun navigateToDetail(product: ProductX) {
        val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(product)
        findNavController().navigate(action)
    }

    private fun observeLiveData() {
        with(viewModel) {
            productLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    it.products?.let { it1 -> productAdapter.updateProductList(it1) }
                }.doOnFailure {
                    showError(it)
                }
            }
            searchProductLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    it.products?.let { it1 -> productAdapter.updateProductList(it1) }
                }.doOnFailure {
                    showError(it)
                }
            }
            categoryProductLiveData.observe(viewLifecycleOwner) {
                it.doOnSuccess {
                    it.products?.let { it1 -> productAdapter.updateProductList(it1) }
                }.doOnFailure {
                    showError(it)
                }
            }
            categoryNameLiveData.observe(viewLifecycleOwner) {
                val addCategory: MutableList<String> = mutableListOf()
                addCategory.add("All")
                it.doOnSuccess {
                    addCategory.addAll(it.categories)
                    categoryNameAdapter.updateCategoryName(addCategory)
                }.doOnFailure {
                    showError(it)
                }
            }
        }
    }

    private fun search() {
        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchProduct(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.searchProduct(newText)
                return true
            }
        })
    }

    private fun showError(error: Throwable) {
        showSnackBar(error.message ?: "Unexpected error")
    }
}