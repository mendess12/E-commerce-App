package com.yusufmendes.sisterslabgraduationproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.FavoritesItemBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class FavoriteAdapter(
    private val onClick: (Int) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val favoriteProductList = ArrayList<ProductX>()

    inner class FavoriteViewHolder(private val binding: FavoritesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteProduct: ProductX) {
            with(binding) {
                favoriteItemTitleTv.text = favoriteProduct.title
                favoriteItemPriceTv.text =
                    binding.root.context.getString(R.string.price, favoriteProduct.price.toString())
                favoriteItemIv.downloadFromUrl(favoriteProduct.imageOne)
                if (favoriteProduct.saleState) {
                    favoriteItemSalePriceTv.visibility = View.VISIBLE
                    favoriteItemSalePriceTv.text =
                        binding.root.context.getString(
                            R.string.price,
                            favoriteProduct.salePrice.toString()
                        )
                    favoriteItemPriceTv.setBackgroundResource(R.drawable.discount_line)
                } else {
                    favoriteItemSalePriceTv.visibility = View.GONE
                    favoriteItemPriceTv.background = null
                }
                favoriteItemDeleteIv.setOnClickListener {
                    onClick.invoke(favoriteProduct.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding =
            FavoritesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = favoriteProductList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteProduct = favoriteProductList[position]
        holder.bind(favoriteProduct)
    }

    fun updateList(updateFavoriteProductList: List<ProductX>) {
        favoriteProductList.clear()
        favoriteProductList.addAll(updateFavoriteProductList)
        notifyDataSetChanged()
    }
}