package com.yusufmendes.sisterslabgraduationproject.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.adapter.ProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentHomeBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackbar
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
        filterCategory()
        search()
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
                view?.showSnackbar("Ürün listesi boş")
            }
        }
        viewModel.searchProductLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                productAdapter.updateProductList(it)
            } else {
                view?.showSnackbar("Arama başarısız")
            }
        }
        viewModel.categoryProductLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                productAdapter.updateProductList(it)
            } else {
                view?.showSnackbar("Category listesi boş")
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

    private fun filterCategory() {
        with(binding) {
            homeAllTv.setOnClickListener {
                viewModel.getProducts()
                homeAllTv.setBackgroundResource(R.drawable.category_background)
                homeNotebookTv.background = null
                homeMonitorTv.background = null
                homeConsoleTv.background = null
                homeDesktopTv.background = null
                homeHeadsetTv.background = null
            }
            homeNotebookTv.setOnClickListener {
                viewModel.getCategory("Notebook")
                homeNotebookTv.setBackgroundResource(R.drawable.category_background)
                homeAllTv.background = null
                homeMonitorTv.background = null
                homeConsoleTv.background = null
                homeDesktopTv.background = null
                homeHeadsetTv.background = null
            }
            homeMonitorTv.setOnClickListener {
                viewModel.getCategory("Monitor")
                homeMonitorTv.setBackgroundResource(R.drawable.category_background)
                homeAllTv.background = null
                homeNotebookTv.background = null
                homeConsoleTv.background = null
                homeDesktopTv.background = null
                homeHeadsetTv.background = null

            }
            homeConsoleTv.setOnClickListener {
                viewModel.getCategory("Console")
                homeConsoleTv.setBackgroundResource(R.drawable.category_background)
                homeAllTv.background = null
                homeNotebookTv.background = null
                homeMonitorTv.background = null
                homeDesktopTv.background = null
                homeHeadsetTv.background = null
            }
            homeDesktopTv.setOnClickListener {
                viewModel.getCategory("Desktop")
                homeDesktopTv.setBackgroundResource(R.drawable.category_background)
                homeAllTv.background = null
                homeNotebookTv.background = null
                homeMonitorTv.background = null
                homeConsoleTv.background = null
                homeHeadsetTv.background = null
            }
            homeHeadsetTv.setOnClickListener {
                viewModel.getCategory("Headset")
                homeHeadsetTv.setBackgroundResource(R.drawable.category_background)
                homeAllTv.background = null
                homeNotebookTv.background = null
                homeMonitorTv.background = null
                homeConsoleTv.background = null
                homeDesktopTv.background = null
            }
        }
    }
}