package com.yusufmendes.sisterslabgraduationproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class ProductEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId")
    val productId: Int,

    @ColumnInfo(name = "imageOne")
    val imageOne: String,

    @ColumnInfo(name = "price")
    val price: Double,

    @ColumnInfo(name = "salePrice")
    val salePrice: Double,

    @ColumnInfo(name = "saleState")
    val saleState: Boolean,

    @ColumnInfo(name = "title")
    val title: String
)
