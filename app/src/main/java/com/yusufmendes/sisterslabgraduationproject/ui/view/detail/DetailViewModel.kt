package com.yusufmendes.sisterslabgraduationproject.ui.view.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
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

    var addBagLiveData = MutableLiveData<AppResult<CRUD>>()

    fun addToBag(addToCardRequest: AddToCardRequest) {
        viewModelScope.launch {
            val result: AppResult<CRUD> = addToBagUseCase(addToCardRequest)
            addBagLiveData.postValue(result)
        }
    }
}