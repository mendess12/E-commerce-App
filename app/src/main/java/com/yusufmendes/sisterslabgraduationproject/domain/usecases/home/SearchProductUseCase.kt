package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import javax.inject.Inject

class SearchProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<SearchProductParams, AppResult<Product>>() {
    override suspend fun execute(params: SearchProductParams): AppResult<Product> =
        productRepository.searchProduct(params.query)
}

data class SearchProductParams(val query: String)