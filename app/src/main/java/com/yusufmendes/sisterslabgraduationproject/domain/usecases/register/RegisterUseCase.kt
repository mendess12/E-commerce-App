package com.yusufmendes.sisterslabgraduationproject.domain.usecases.register

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.RegisterBody
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<RegisterBody, AppResult<CRUD>>() {
    override suspend fun execute(params: RegisterBody): AppResult<CRUD> =
        productRepository.register(params)
}