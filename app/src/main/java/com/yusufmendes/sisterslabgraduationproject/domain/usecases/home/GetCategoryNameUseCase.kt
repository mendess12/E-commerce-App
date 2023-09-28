package com.yusufmendes.sisterslabgraduationproject.domain.usecases.home

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.Category
import retrofit2.Response
import javax.inject.Inject

class GetCategoryNameUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<Unit, Response<Category>>() {
    override suspend fun execute(params: Unit): Response<Category> {
        return productRepository.getCategoryName()
    }
}