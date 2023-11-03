package com.yusufmendes.sisterslabgraduationproject.domain.usecases.favoirte

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity
import javax.inject.Inject

class GetFavoriteProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<Unit, List<ProductEntity>>() {
    override suspend fun execute(params: Unit): List<ProductEntity> =
        productRepository.getFavoriteProducts()
}