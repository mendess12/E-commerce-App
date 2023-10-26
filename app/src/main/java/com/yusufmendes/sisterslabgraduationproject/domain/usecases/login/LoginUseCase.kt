package com.yusufmendes.sisterslabgraduationproject.domain.usecases.login

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.LoginBody
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<LoginBody, AppResult<CRUD>>() {
    override suspend fun execute(params: LoginBody): AppResult<CRUD> =
        productRepository.login(params)
}