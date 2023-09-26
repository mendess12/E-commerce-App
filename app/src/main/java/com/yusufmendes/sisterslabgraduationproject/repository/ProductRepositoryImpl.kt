package com.yusufmendes.sisterslabgraduationproject.repository

import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.DeleteCartRequest
import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.services.ProductAPI
import retrofit2.Response
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productAPI: ProductAPI) :
    ProductRepository {

    override suspend fun getProducts(): Response<Product> = productAPI.getProductData()

    override suspend fun getBagProducts(): Response<Product> = productAPI.getBagProductsData()

    override suspend fun searchProduct(query: String): Response<Product> =
        productAPI.searchProduct(query)

    override suspend fun addToBag(addToCardRequest: AddToCardRequest): Response<CRUD> =
        productAPI.addToBag(addToCardRequest)

    override suspend fun deleteToProductFromBag(itemId: Int): Response<CRUD> =
        productAPI.deleteProductFromBag(DeleteCartRequest(itemId))

    override suspend fun getCategory(category: String): Response<Product> =
        productAPI.getCategory(category)
}