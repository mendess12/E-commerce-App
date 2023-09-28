package com.yusufmendes.sisterslabgraduationproject.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yusufmendes.sisterslabgraduationproject.databinding.CategoryNameItemBinding

class CategoryNameAdapter(private val onClick: (String) -> Unit) :
    RecyclerView.Adapter<CategoryNameAdapter.CategoryNameViewHolder>() {

    private val categoryNameList = ArrayList<String>()
    private var selectedPosition = 0

    inner class CategoryNameViewHolder(private val binding: CategoryNameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryName: String) {
            with(binding) {
                categoryCheckFilter.text = categoryName
                categoryCheckFilter.setOnClickListener {
                    onClick.invoke(categoryName)
                    selectedPosition = layoutPosition
                    notifyItemRangeChanged(0, categoryNameList.size - 1)
                }
            }
            binding.categoryCheckFilter.isChecked = selectedPosition == layoutPosition
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryNameViewHolder {
        val binding =
            CategoryNameItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryNameViewHolder(binding)
    }

    override fun getItemCount(): Int = categoryNameList.size

    override fun onBindViewHolder(holder: CategoryNameViewHolder, position: Int) {
        val category = categoryNameList[position]
        holder.bind(category)
    }

    fun updateCategoryName(updateCategoryNameList: List<String>) {
        categoryNameList.clear()
        categoryNameList.addAll(updateCategoryNameList)
        Log.e("category name list", categoryNameList.toString())
        notifyDataSetChanged()
    }
}