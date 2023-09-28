package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<Unit, Response<Product>>() {
    override suspend fun execute(params: Unit): Response<Product> = productRepository.getProducts()
}