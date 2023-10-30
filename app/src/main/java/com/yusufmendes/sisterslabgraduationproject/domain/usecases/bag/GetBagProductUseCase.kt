package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import javax.inject.Inject

class GetBagProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<BagParams, AppResult<Product>>() {
    override suspend fun execute(params: BagParams): AppResult<Product> =
        productRepository.getBagProducts(params.userId)
}

data class BagParams(val userId: String)