package com.yusufmendes.sisterslabgraduationproject.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    var addBagLiveData = MutableLiveData<CRUD?>()

    fun addToBag(addToCardRequest: AddToCardRequest) {
        viewModelScope.launch {
            try {
                val response = productRepository.addToBag(addToCardRequest)
                if (response.isSuccessful) {
                    addBagLiveData.postValue(response.body())
                } else {
                    addBagLiveData.postValue(null)
                }
            } catch (e: Exception) {
                addBagLiveData.postValue(null)
            }
        }
    }
}