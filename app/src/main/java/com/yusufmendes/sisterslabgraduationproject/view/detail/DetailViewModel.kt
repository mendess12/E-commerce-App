package com.yusufmendes.sisterslabgraduationproject.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.detail.AddToBagUseCase
import com.yusufmendes.sisterslabgraduationproject.model.AddToCardRequest
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val addToBagUseCase: AddToBagUseCase
) :
    ViewModel() {

    var addBagLiveData = MutableLiveData<CRUD?>()

    fun addToBag(addToCardRequest: AddToCardRequest) {
        viewModelScope.launch {
            try {
                val response = addToBagUseCase(addToCardRequest)
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