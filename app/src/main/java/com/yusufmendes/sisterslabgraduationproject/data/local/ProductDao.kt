package com.yusufmendes.sisterslabgraduationproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM favorite_products")
    suspend fun getFavoriteProduct(): List<ProductEntity>

    /*OnConflictStrategy.REPLACE eklenecek ürün daha önceden favoriler
    * listesinde varsa tekrar ekleme yapmak yerine var olan veri
    *  üzerine yazma işlemi için kullanılır
    */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteFavoriteProduct(productEntity: ProductEntity)
}