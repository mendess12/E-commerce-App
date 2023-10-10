package com.yusufmendes.sisterslabgraduationproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.ProductItemBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class ProductAdapter(
    private val onClick: (ProductX) -> Unit
) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productList = ArrayList<ProductX>()

    inner class ProductViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductX) {
            with(binding) {
                productItemTitleTv.text = product.title
                productItemPriceTv.text =
                    binding.root.context.getString(R.string.price, product.price.toString())
                productItemIv.downloadFromUrl(product.imageOne)
                if (product.saleState) {
                    productItemSalePriceTv.visibility = View.VISIBLE
                    productItemSalePriceTv.text =
                        binding.root.context.getString(
                            R.string.price,
                            product.salePrice.toString()
                        )
                    productItemPriceTv.setBackgroundResource(R.drawable.discount_line)
                } else {
                    productItemSalePriceTv.visibility = View.GONE
                    productItemPriceTv.background = null
                }
                itemView.setOnClickListener {
                    onClick.invoke(product)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    fun updateProductList(updateProductList: List<ProductX>) {
        productList.clear()
        productList.addAll(updateProductList)
        notifyDataSetChanged()
    }
}