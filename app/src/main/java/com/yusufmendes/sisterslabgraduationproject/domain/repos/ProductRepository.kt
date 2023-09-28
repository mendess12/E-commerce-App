package com.yusufmendes.sisterslabgraduationproject.domain.repos

import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Category
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response

interface ProductRepository {

    suspend fun getProducts(): Response<Product>

    suspend fun getBagProducts(): Response<Product>

    suspend fun searchProduct(query: String): Response<Product>

    suspend fun addToBag(addToCardRequest: AddToCardRequest): Response<CRUD>

    suspend fun deleteToProductFromBag(itemId: Int): Response<CRUD>

    suspend fun getCategory(category: String): Response<Product>

    suspend fun getCategoryName(): Response<Category>
}