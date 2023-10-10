package com.yusufmendes.sisterslabgraduationproject.ui.view.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.CategoryProductParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetCategoryNameUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetCategoryUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.GetProductUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductUseCase
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val searchProductUseCase: SearchProductUseCase,
    private val categoryUseCase: GetCategoryUseCase,
    private val categoryNameUseCase: GetCategoryNameUseCase
) :
    ViewModel() {

    val productLiveData = MutableLiveData<List<ProductX>?>()
    val searchProductLiveData = MutableLiveData<List<ProductX>?>()
    val categoryProductLiveData = MutableLiveData<List<ProductX>?>()
    val categoryNameLiveData = MutableLiveData<List<String>?>()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch {
            try {
                val response = getProductUseCase(Unit)
                if (response.isSuccessful) {
                    productLiveData.postValue(response.body()?.products)
                } else {
                    productLiveData.postValue(null)
                }
            } catch (e: Exception) {
                productLiveData.postValue(null)
            }
        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            try {
                val response = searchProductUseCase(SearchProductParams(query))
                if (response.isSuccessful) {
                    searchProductLiveData.postValue(response.body()?.products)
                } else {
                    searchProductLiveData.postValue(null)
                }
            } catch (e: Exception) {
                searchProductLiveData.postValue(null)
            }
        }
    }

    fun getCategory(category: String) {
        if (category == "All") return getProducts()
        viewModelScope.launch {
            try {
                val response = categoryUseCase(CategoryProductParams(category))
                if (response.isSuccessful) {
                    categoryProductLiveData.postValue(response.body()?.products)
                } else {
                    categoryProductLiveData.postValue(null)
                }
            } catch (e: Exception) {
                categoryProductLiveData.postValue(null)
            }
        }
    }

    fun getCategoryName() {
        val addCategory: MutableList<String> = mutableListOf()
        addCategory.add("All")
        viewModelScope.launch {
            try {
                val response = categoryNameUseCase(Unit)
                if (response.isSuccessful) {
                    response.body()?.let { addCategory.addAll(it.categories) }
                    categoryNameLiveData.postValue(addCategory)
                } else {
                    categoryNameLiveData.postValue(null)
                }
            } catch (e: Exception) {
                categoryNameLiveData.postValue(null)
            }
        }
    }
}