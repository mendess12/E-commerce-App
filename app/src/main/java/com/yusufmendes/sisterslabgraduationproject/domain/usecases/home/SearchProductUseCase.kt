package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<SearchProductParams, Response<Product>>() {
    override suspend fun execute(params: SearchProductParams): Response<Product> {
        return productRepository.searchProduct(params.query)
    }
}

data class SearchProductParams(val query: String)