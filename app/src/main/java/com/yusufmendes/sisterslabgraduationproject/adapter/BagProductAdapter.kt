package com.yusufmendes.sisterslabgraduationproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.databinding.BagItemBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class BagProductAdapter : RecyclerView.Adapter<BagProductAdapter.BagViewHolder>() {

    private val bagProductList = ArrayList<ProductX>()

    inner class BagViewHolder(val binding: BagItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(bagProduct: ProductX) {
            with(binding) {
                bagItemTitleTv.text = bagProduct.title
                bagItemPriceTv.text = bagProduct.price.toString()
                bagItemIv.downloadFromUrl(bagProduct.imageOne)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BagViewHolder {
        val binding = BagItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BagViewHolder(binding)
    }

    override fun getItemCount(): Int = bagProductList.size

    override fun onBindViewHolder(holder: BagViewHolder, position: Int) {
        val bagProduct = bagProductList[position]
        holder.bind(bagProduct)
    }

    fun updateProductList(updateBagProductList: List<ProductX>) {
        bagProductList.clear()
        bagProductList.addAll(updateBagProductList)
        notifyDataSetChanged()
    }
}