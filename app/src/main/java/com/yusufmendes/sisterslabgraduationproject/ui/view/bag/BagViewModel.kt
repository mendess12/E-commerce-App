package com.yusufmendes.sisterslabgraduationproject.ui.view.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.BagParams
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.ClearBagUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.DeleteToProductFromBagUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.GetBagProductUseCase
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BagViewModel @Inject constructor(
    private val getBagProductUseCase: GetBagProductUseCase,
    private val deleteToProductFromBagUseCase: DeleteToProductFromBagUseCase,
    private val clearBagUseCase: ClearBagUseCase
) :
    ViewModel() {

    var bagLiveData = MutableLiveData<AppResult<Product>>()
    val deleteLiveData = MutableLiveData<AppResult<CRUD>>()
    var clearBagLiveData = MutableLiveData<AppResult<Unit>>()

    fun getBagProducts(userId: String) {
        viewModelScope.launch {
            val result = getBagProductUseCase(BagParams(userId))
            bagLiveData.postValue(result)

        }
    }

    fun deleteProduct(itemId: Int, userId: String) {
        viewModelScope.launch {
            val result = deleteToProductFromBagUseCase(itemId)
            deleteLiveData.postValue(result)
            getBagProducts(userId)
        }
    }

    fun clearBag(userId: String) {
        val itemsInCart: AppResult<Product> = bagLiveData.value ?: return
        /* viewModelScope.launch {
             val result = clearBagUseCase(itemsInCart.get().products).doOnSuccess {
                 getBagProducts(userId)
             }
             clearBagLiveData.postValue(result)
         }*/
    }
}