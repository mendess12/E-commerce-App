package com.yusufmendes.sisterslabgraduationproject.data.remote

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Category
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagBody
import com.yusufmendes.sisterslabgraduationproject.model.DeleteCartRequest
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import com.yusufmendes.sisterslabgraduationproject.model.LoginResponse
import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.model.RegisterBody
import com.yusufmendes.sisterslabgraduationproject.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductAPI {

    @GET("get_products.php")
    suspend fun getProductData(): AppResult<Product>

    @GET("get_cart_products.php")
    suspend fun getBagProductsData(
        @Query("userId") userId: String
    ): AppResult<Product>

    @GET("get_sale_products.php")
    suspend fun getSaleProductData(): AppResult<Product>

    @GET("search_product.php")
    suspend fun searchProduct(
        @Query("query") query: String
    ): AppResult<Product>

    @POST("add_to_cart.php")
    suspend fun addToBag(
        @Body addToCardRequest: AddToCardRequest
    ): AppResult<CRUD>

    @POST("delete_from_cart.php")
    suspend fun deleteProductFromBag(
        @Body deleteCartRequest: DeleteCartRequest
    ): AppResult<CRUD>

    @GET("get_products_by_category.php")
    suspend fun getCategory(
        @Query("category") category: String
    ): AppResult<Product>

    @GET("get_categories.php")
    suspend fun getCategoryName(): AppResult<Category>

    @POST("sign_in.php")
    suspend fun login(
        @Body loginBody: LoginBody
    ): AppResult<LoginResponse>

    @POST("sign_up.php")
    suspend fun register(
        @Body registerBody: RegisterBody
    ): AppResult<CRUD>

    @GET("get_user.php")
    suspend fun getUser(
        @Query("userId") userId: String
    ): AppResult<UserResponse>

    @POST("clear_cart.php")
    suspend fun clearBag(
        @Body clearBagBody: ClearBagBody
    ): AppResult<CRUD>
}