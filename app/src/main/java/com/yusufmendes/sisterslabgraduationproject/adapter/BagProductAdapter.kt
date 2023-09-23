package com.yusufmendes.sisterslabgraduationproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.BagItemBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class BagProductAdapter : RecyclerView.Adapter<BagProductAdapter.BagViewHolder>() {

    private val bagProductList = ArrayList<ProductX>()

    inner class BagViewHolder(private val binding: BagItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bagProduct: ProductX) {
            with(binding) {
                bagItemTitleTv.text = bagProduct.title
                bagItemPriceTv.text =
                    binding.root.context.getString(R.string.price, bagProduct.price.toString())
                bagItemIv.downloadFromUrl(bagProduct.imageOne)
                if (bagProduct.saleState) {
                    bagItemSalePriceTv.visibility = View.VISIBLE
                    bagItemSalePriceTv.text =
                        binding.root.context.getString(
                            R.string.price,
                            bagProduct.salePrice.toString()
                        )
                    bagItemPriceTv.setBackgroundResource(R.drawable.discount_line)
                }
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