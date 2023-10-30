package com.yusufmendes.sisterslabgraduationproject.domain.usecases.login

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import com.yusufmendes.sisterslabgraduationproject.model.LoginResponse
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<LoginBody, AppResult<LoginResponse>>() {
    override suspend fun execute(params: LoginBody): AppResult<LoginResponse> =
        productRepository.login(params)
}