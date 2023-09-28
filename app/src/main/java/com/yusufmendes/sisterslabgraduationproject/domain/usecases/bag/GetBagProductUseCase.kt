package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import javax.inject.Inject

class GetBagProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<Unit, Response<Product>>() {
    override suspend fun execute(params: Unit): Response<Product> =
        productRepository.getBagProducts()
}