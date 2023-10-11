package com.yusufmendes.sisterslabgraduationproject.ui.view.bag

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
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
    var clearBagLiveData = MutableLiveData<AppResult<Unit>>()

    fun getBagProducts() {
        viewModelScope.launch {
            getBagProductUseCase(Unit).doOnSuccess {
                bagLiveData.postValue(it.products)
            }.doOnFailure {
                bagLiveData.postValue(null)
            }
        }
    }

    fun deleteProduct(itemId: Int) {
        viewModelScope.launch {
            deleteToProductFromBagUseCase(itemId).doOnSuccess {
                removeItemFromVisibleList(itemId)
                deleteLiveData.postValue(it)
            }.doOnFailure {
                deleteLiveData.postValue(null)
            }
        }
    }

    fun clearBag() {
        val itemsInCart = bagLiveData.value ?: return
        viewModelScope.launch {
            val result = clearBagUseCase(itemsInCart).doOnSuccess {
                getBagProducts()
            }
            clearBagLiveData.postValue(result)
        }
    }

    private fun removeItemFromVisibleList(deletedItemId: Int) {
        val productList = bagLiveData.value ?: return
        val newProductList = productList.filter { it.id != deletedItemId }
        bagLiveData.postValue(newProductList)
    }
}