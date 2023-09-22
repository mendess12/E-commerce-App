package com.yusufmendes.sisterslabgraduationproject.repository

import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.services.ProductAPI
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productAPI: ProductAPI) {

    suspend fun getProducts(): Response<Product> = productAPI.getProductData()

    suspend fun getBagProducts(): Response<Product> = productAPI.getBagProductsData()
}