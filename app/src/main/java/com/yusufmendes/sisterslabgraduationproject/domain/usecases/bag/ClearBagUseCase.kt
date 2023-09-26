package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import com.yusufmendes.sisterslabgraduationproject.model.ClearBagRequest
import retrofit2.Response
import javax.inject.Inject

class ClearBagUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<ClearBagRequest, Response<CRUD>>() {
    override suspend fun execute(params: ClearBagRequest): Response<CRUD> {
        return productRepository.clearBagRequest(params)
    }
}