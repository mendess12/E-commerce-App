package com.yusufmendes.sisterslabgraduationproject.services

import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductAPI {

    @GET("get_products.php")
    suspend fun getProductData(): Response<Product>
}