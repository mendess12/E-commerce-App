package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import javax.inject.Inject

class GetSaleProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<Unit, AppResult<Product>>() {
    override suspend fun execute(params: Unit): AppResult<Product> =
        productRepository.getSaleProduct()
}