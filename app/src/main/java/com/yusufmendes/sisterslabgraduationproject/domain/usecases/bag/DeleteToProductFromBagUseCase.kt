package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.CRUD
import retrofit2.Response
import javax.inject.Inject

class DeleteToProductFromBagUseCase @Inject constructor(
    private val productRepository: ProductRepository
) :
    SuspendUseCase<Int, Response<CRUD>>() {
    override suspend fun execute(params: Int): Response<CRUD> =
        productRepository.deleteToProductFromBag(params)
}