package com.yusufmendes.sisterslabgraduationproject.ui.view.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.profile.ProfileUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.usecases.profile.UseParams
import com.yusufmendes.sisterslabgraduationproject.model.UserResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    var getUserLiveData = MutableLiveData<AppResult<UserResponse>>()

    fun getUser(userId:String){
        viewModelScope.launch {
            val result = profileUseCase(UseParams(userId))
            getUserLiveData.postValue(result)
        }
    }
}