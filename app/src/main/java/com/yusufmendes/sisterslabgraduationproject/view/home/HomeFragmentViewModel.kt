package com.yusufmendes.sisterslabgraduationproject.view.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.repository.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val productRepository: ProductRepository
) :
    ViewModel() {

    val productLiveData = MutableLiveData<List<ProductX>?>()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            try {
                val response = productRepository.getProducts()
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
}