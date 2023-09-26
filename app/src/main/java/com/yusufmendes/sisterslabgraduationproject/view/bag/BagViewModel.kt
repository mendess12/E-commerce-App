package com.yusufmendes.sisterslabgraduationproject.view.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.ClearBagUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.DeleteToProductFromBagUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag.GetBagProductUseCase
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
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

    var bagLiveData = MutableLiveData<List<ProductX>?>()
    val deleteLiveData = MutableLiveData<CRUD?>()
    var clearBagLiveData = MutableLiveData<Boolean>()

    fun getBagProducts() {
        viewModelScope.launch {
            try {
                val response = getBagProductUseCase(Unit)
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

    fun deleteProduct(itemId: Int) {
        viewModelScope.launch {
            try {
                val response = deleteToProductFromBagUseCase(itemId)
                if (response.isSuccessful) {
                    removeItemFromVisibleList(itemId)
                    deleteLiveData.postValue(response.body())
                } else {
                    deleteLiveData.postValue(null)
                }
            } catch (e: Exception) {
                deleteLiveData.postValue(null)
            }
        }
    }

    fun clearBag() {
        val itemsInCart = bagLiveData.value ?: return
        viewModelScope.launch {
            try {
                val response = clearBagUseCase(itemsInCart)
                if (response) {
                    getBagProducts()
                    clearBagLiveData.postValue(response)
                } else {
                    clearBagLiveData.postValue(false)
                }
            } catch (e: Exception) {
                clearBagLiveData.postValue(false)
            }
        }
    }

    private fun removeItemFromVisibleList(deletedItemId: Int) {
        val productList = bagLiveData.value ?: return
        val newProductList = productList.filter { it.id != deletedItemId }
        bagLiveData.postValue(newProductList)
    }
}