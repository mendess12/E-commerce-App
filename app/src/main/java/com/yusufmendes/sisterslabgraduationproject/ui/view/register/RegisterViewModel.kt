package com.yusufmendes.sisterslabgraduationproject.ui.view.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.register.RegisterUseCase
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.RegisterBody
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    var registerLiveData = MutableLiveData<AppResult<CRUD>>()

    fun register(registerBody: RegisterBody) {
        viewModelScope.launch {
            val result = registerUseCase(registerBody)
            registerLiveData.postValue(result)
        }
    }
}