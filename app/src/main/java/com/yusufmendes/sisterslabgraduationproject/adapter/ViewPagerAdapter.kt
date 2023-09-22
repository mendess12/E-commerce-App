package com.yusufmendes.sisterslabgraduationproject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yusufmendes.sisterslabgraduationproject.databinding.ItemViewPagerBinding
import com.yusufmendes.sisterslabgraduationproject.util.downloadFromUrl

class ViewPagerAdapter(
    private val imageList: ArrayList<String>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {


    inner class ViewPagerViewHolder(val binding: ItemViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {
            binding.viewPagerIv.downloadFromUrl(imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val binding =
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerViewHolder(binding)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        holder.bind(imageList[position])
        if (position == imageList.size - 1)
            viewPager2.post(runnable)
    }

    private val runnable = Runnable {
        imageList.addAll(imageList)
        notifyDataSetChanged()
    }
}