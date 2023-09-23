package com.yusufmendes.sisterslabgraduationproject.services

import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductAPI {

    @GET("get_products.php")
    suspend fun getProductData(): Response<Product>

    @GET("get_cart_products.php?userId=b3sa6dj721312ssadas21d")
    suspend fun getBagProductsData(): Response<Product>
    @GET("search_product.php")
    suspend fun searchProduct(
        @Query("query") query: String
    ): Response<Product>
}