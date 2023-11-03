package com.yusufmendes.sisterslabgraduationproject.data.di

import android.content.Context
import androidx.room.Room
import com.yusufmendes.sisterslabgraduationproject.data.local.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Provides
    @Singleton
    fun provideProductRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "products_favorite").build()

    @Provides
    @Singleton
    fun provideProductDao(roomDB: ProductRoomDB) = roomDB.productDao()
}