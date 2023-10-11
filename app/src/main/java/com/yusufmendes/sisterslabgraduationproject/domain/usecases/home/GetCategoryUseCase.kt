package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Product
import retrofit2.Response
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<CategoryProductParams, AppResult<Product>>() {
    override suspend fun execute(params: CategoryProductParams): AppResult<Product> =
        productRepository.getCategory(params.category)
}

data class CategoryProductParams(val category: String)