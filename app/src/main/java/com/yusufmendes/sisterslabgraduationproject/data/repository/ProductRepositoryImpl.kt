package com.yusufmendes.sisterslabgraduationproject.data.repository

import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Category
import com.yusufmendes.sisterslabgraduationproject.model.DeleteCartRequest
import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.data.remote.ProductAPI
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagBody
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import com.yusufmendes.sisterslabgraduationproject.model.LoginResponse
import com.yusufmendes.sisterslabgraduationproject.model.RegisterBody
import com.yusufmendes.sisterslabgraduationproject.model.UserResponse
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productAPI: ProductAPI) :
    ProductRepository {

    override suspend fun getProducts(): AppResult<Product> = productAPI.getProductData()

    override suspend fun getBagProducts(userId: String): AppResult<Product> =
        productAPI.getBagProductsData(userId)

    override suspend fun getSaleProduct(): AppResult<Product> = productAPI.getSaleProductData()

    override suspend fun searchProduct(query: String): AppResult<Product> =
        productAPI.searchProduct(query)

    override suspend fun addToBag(addToCardRequest: AddToCardRequest): AppResult<CRUD> =
        productAPI.addToBag(addToCardRequest)

    override suspend fun deleteToProductFromBag(itemId: Int): AppResult<CRUD> =
        productAPI.deleteProductFromBag(DeleteCartRequest(itemId))

    override suspend fun clearBag(clearBagBody: ClearBagBody): AppResult<CRUD> =
        productAPI.clearBag(clearBagBody)

    override suspend fun getCategory(category: String): AppResult<Product> =
        productAPI.getCategory(category)

    override suspend fun getCategoryName(): AppResult<Category> = productAPI.getCategoryName()

    override suspend fun login(loginBody: LoginBody): AppResult<LoginResponse> =
        productAPI.login(loginBody)

    override suspend fun register(registerBody: RegisterBody): AppResult<CRUD> =
        productAPI.register(registerBody)

    override suspend fun getUser(userId: String): AppResult<UserResponse> =
        productAPI.getUser(userId)
}