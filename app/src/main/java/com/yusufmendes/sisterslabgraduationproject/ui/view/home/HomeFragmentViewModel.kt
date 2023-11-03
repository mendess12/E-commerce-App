package com.yusufmendes.sisterslabgraduationproject.ui.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.favoirte.AddFavoriteProductUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.CategoryProductParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetCategoryNameUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetCategoryUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetProductUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetSaleProductUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductUseCase
import com.yusufmendes.sisterslabgraduationproject.model.Category
import com.yusufmendes.sisterslabgraduationproject.model.Product
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val getSaleProductUseCase: GetSaleProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
    private val categoryUseCase: GetCategoryUseCase,
    private val categoryNameUseCase: GetCategoryNameUseCase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase
) : ViewModel() {

    val productLiveData = MutableLiveData<AppResult<Product>>()
    val saleProductLiveData = MutableLiveData<AppResult<Product>>()
    val searchProductLiveData = MutableLiveData<AppResult<Product>>()
    val categoryProductLiveData = MutableLiveData<AppResult<Product>>()
    val categoryNameLiveData = MutableLiveData<AppResult<Category>>()

    init {
        getProducts()
    }

    private fun getProducts() {
        Log.e("HomeFragment", "getProducts")
        viewModelScope.launch {
            val result = getProductUseCase(Unit)
            productLiveData.postValue(result)
        }
    }

    fun addFavoriteProduct(productEntity: ProductEntity) {
        viewModelScope.launch {
            addFavoriteProductUseCase(productEntity)

        }
    }

    fun getSaleProducts() {
        viewModelScope.launch {
            val result = getSaleProductUseCase(Unit)
            saleProductLiveData.postValue(result)
        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            val result = searchProductUseCase(SearchProductParams(query))
            searchProductLiveData.postValue(result)
        }
    }

    fun getCategory(category: String) {
        if (category == "All") return getProducts()
        viewModelScope.launch {
            val result = categoryUseCase(CategoryProductParams(category))
            categoryProductLiveData.postValue(result)
        }
    }

    fun getCategoryName() {
        val addCategory: MutableList<String> = mutableListOf()
        addCategory.add("All")
        viewModelScope.launch {
            val result = categoryNameUseCase(Unit)
            categoryNameLiveData.postValue(result)
        }
    }
}