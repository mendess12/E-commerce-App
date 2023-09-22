package com.yusufmendes.sisterslabgraduationproject.view.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import com.yusufmendes.sisterslabgraduationproject.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    var bagLiveData = MutableLiveData<List<ProductX>?> ()

    init {
        getBagProducts()
    }

    private fun getBagProducts(){
        viewModelScope.launch {
            try {
                val response = productRepository.getBagProducts()
                if (response.isSuccessful) {
                    bagLiveData.postValue(response.body()?.products)
                } else {
                    bagLiveData.postValue(null)
                }
            } catch (e: Exception) {
                bagLiveData.postValue(null)
            }
        }
    }
}