package com.yusufmendes.sisterslabgraduationproject.ui.view.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FragmentFavoriteBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity
import com.yusufmendes.sisterslabgraduationproject.ui.adapter.FavoriteAdapter
import com.yusufmendes.sisterslabgraduationproject.util.extensions.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment(R.layout.fragment_favorite) {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var favoriteAdapter: FavoriteAdapter
    private val viewModel: FavoriteFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavoriteBinding.bind(view)

        binding.favoriteScreenToolbar.bagToolbarDeleteIv.visibility = View.GONE
        binding.favoriteScreenToolbar.bagToolbarTitleTv.setText(R.string.favorite)

        favoriteAdapter = FavoriteAdapter(::deleteFavoriteProduct)
        with(binding) {
            favoriteRv.setHasFixedSize(true)
            favoriteRv.layoutManager =
                LinearLayoutManager(requireContext())
            favoriteRv.adapter = favoriteAdapter
        }
        viewModel.getFavorite()
        observeLiveData()
    }

    private fun deleteFavoriteProduct(productEntity: ProductEntity) {
        viewModel.deleteProductFromFavorite(productEntity)
    }

    private fun observeLiveData() {
        viewModel.getFavoriteLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                if (it.isNotEmpty()){
                    favoriteAdapter.updateList(it)
                    binding.favoriteMessageTv.visibility = View.GONE
                    binding.favoriteRv.visibility = View.VISIBLE
                }else{
                    binding.favoriteMessageTv.visibility = View.VISIBLE
                    binding.favoriteRv.visibility = View.GONE
                }

            } else {
                view?.showSnackBar("List empty")
            }
        }
        viewModel.deleteProductFromFavoriteLiveData.observe(viewLifecycleOwner) {
            if (it != null) {

                view?.showSnackBar("Product deleted")
            } else {
                view?.showSnackBar("Product didn't delete")
            }
        }
    }
}