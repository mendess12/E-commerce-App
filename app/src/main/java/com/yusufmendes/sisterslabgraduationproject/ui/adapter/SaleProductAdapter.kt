package com.yusufmendes.sisterslabgraduationproject.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.R
import com.yusufmendes.sisterslabgraduationproject.databinding.SaleProductItemBinding
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class SaleProductAdapter() : RecyclerView.Adapter<SaleProductAdapter.SaleProductViewHolder>() {

    val saleProductList = ArrayList<ProductX>()

    inner class SaleProductViewHolder(private val binding: SaleProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(saleProduct: ProductX) {
            with(binding) {
                saleProductItemTitleTv.text = saleProduct.title
                saleProductItemPriceTv.text =
                    binding.root.context.getString(R.string.price, saleProduct.price.toString())
                saleProductItemIv.downloadFromUrl(saleProduct.imageOne)
                if (saleProduct.saleState) {
                    saleProductItemSalePriceTv.visibility = View.VISIBLE
                    saleProductItemSalePriceTv.text =
                        binding.root.context.getString(
                            R.string.price,
                            saleProduct.salePrice.toString()
                        )
                    saleProductItemPriceTv.setBackgroundResource(R.drawable.discount_line)
                } else {
                    saleProductItemSalePriceTv.visibility = View.GONE
                    saleProductItemPriceTv.background = null
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleProductViewHolder {
        val binding =
            SaleProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SaleProductViewHolder(binding)
    }

    override fun getItemCount(): Int = saleProductList.size


    override fun onBindViewHolder(holder: SaleProductViewHolder, position: Int) {
        val saleProduct = saleProductList[position]
        holder.bind(saleProduct)
    }

    fun updateProductList(updateSaleProductList: List<ProductX>) {
        saleProductList.clear()
        saleProductList.addAll(updateSaleProductList)
        notifyDataSetChanged()
    }
}