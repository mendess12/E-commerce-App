package com.yusufmendes.sisterslabgraduationproject.ui.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.login.LoginUseCase
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import com.yusufmendes.sisterslabgraduationproject.model.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    var loginLiveData = MutableLiveData<AppResult<LoginResponse>>()

    fun login(loginBody: LoginBody){
        viewModelScope.launch {
            val result = loginUseCase(loginBody)
            loginLiveData.postValue(result)
        }
    }
}