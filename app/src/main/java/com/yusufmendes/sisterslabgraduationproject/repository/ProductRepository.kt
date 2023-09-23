package com.yusufmendes.sisterslabgraduationproject.repository

import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.services.ProductAPI
import retrofit2.Response
import javax.inject.Inject

class ProductRepository @Inject constructor(private val productAPI: ProductAPI) {

    suspend fun getProducts(): Response<Product> = productAPI.getProductData()

    suspend fun getBagProducts(): Response<Product> = productAPI.getBagProductsData()

    suspend fun searchProduct(query: String): Response<Product> = productAPI.searchProduct(query)

    suspend fun addToBag(addToCardRequest: AddToCardRequest): Response<CRUD> =
        productAPI.addToBag(addToCardRequest)


}