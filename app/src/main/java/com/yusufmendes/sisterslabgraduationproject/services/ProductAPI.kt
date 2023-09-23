package com.yusufmendes.sisterslabgraduationproject.services

import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("add_to_cart.php")
    suspend fun addToBag(
        @Body addToCardRequest: AddToCardRequest
    ): Response<CRUD>
}