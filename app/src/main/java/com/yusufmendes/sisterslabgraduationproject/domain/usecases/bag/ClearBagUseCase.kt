package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagBody
import javax.inject.Inject

class ClearBagUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<ClearBagBody, AppResult<CRUD>>() {
    override suspend fun execute(params: ClearBagBody): AppResult<CRUD> =
        productRepository.clearBag(params)
}