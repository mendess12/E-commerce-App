package com.yusufmendes.sisterslabgraduationproject.view.bag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.adapter.BagProductAdapter
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentBagBinding
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
        bagProductAdapter = BagProductAdapter()
        binding.bagRv.adapter = bagProductAdapter
        viewModel.getBagProducts()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.bagLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                bagProductAdapter.updateProductList(it)
            } else {
                Snackbar.make(requireView(), "Liste boş", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}