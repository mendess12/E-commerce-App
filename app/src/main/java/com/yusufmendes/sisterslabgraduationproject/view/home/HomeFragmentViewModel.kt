package com.yusufmendes.sisterslabgraduationproject.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.GetProductUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.home.SearchProductUseCase
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val searchProductUseCase: SearchProductUseCase
) :
    ViewModel() {

    val productLiveData = MutableLiveData<List<ProductX>?>()
    val searchProductLiveData = MutableLiveData<List<ProductX>?>()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val response = getProductUseCase(Unit)
                if (response.isSuccessful) {
                    Log.e("if içi", response.body()?.products.toString())
                    productLiveData.postValue(response.body()?.products)
                } else {
                    Log.e("else içi", response.message())
                    productLiveData.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("catch içi", "sdasda")
                productLiveData.postValue(null)
            }
        }
    }

    fun searchProduct(query: String) {
        viewModelScope.launch {
            try {
                val response = searchProductUseCase(SearchProductParams(query))
                if (response.isSuccessful) {
                    Log.e("if içi", response.body()?.products.toString())
                    searchProductLiveData.postValue(response.body()?.products)
                } else {
                    Log.e("else içi", response.message())
                    searchProductLiveData.postValue(null)
                }
            } catch (e: Exception) {
                Log.e("catch içi", "sdasda")
                searchProductLiveData.postValue(null)
            }
        }
    }
}