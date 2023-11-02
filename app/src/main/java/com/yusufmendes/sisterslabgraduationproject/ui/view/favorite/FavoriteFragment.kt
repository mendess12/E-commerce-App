package com.yusufmendes.sisterslabgraduationproject.ui.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentFavoriteBinding
import com.yusufmendes.sisterslabgraduationproject.ui.adapter.FavoriteAdapter

class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        favoriteAdapter = FavoriteAdapter(::deleteFavoriteProduct)
        with(binding) {
            favoriteRv.setHasFixedSize(true)
            favoriteRv.layoutManager =
                LinearLayoutManager(requireContext())
            favoriteRv.adapter = favoriteAdapter
        }
    }

    private fun deleteFavoriteProduct(productId: Int) {
        //TODO delete product
    }
}