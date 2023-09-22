package com.yusufmendes.sisterslabgraduationproject.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductX(
    val category: String,
    val count: Int,
    val description: String,
    val id: Int,
    val imageOne: String,
    val imageThree: String,
    val imageTwo: String,
    val price: Double,
    val rate: Double,
    val salePrice: Double,
    val saleState: Boolean,
    val title: String
) : Parcelable