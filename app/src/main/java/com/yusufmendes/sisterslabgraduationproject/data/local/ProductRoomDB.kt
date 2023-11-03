package com.yusufmendes.sisterslabgraduationproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity

@Database(entities = [ProductEntity::class], version = 2)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
}