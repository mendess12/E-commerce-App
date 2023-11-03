package com.yusufmendes.sisterslabgraduationproject.domain.usecases.favoirte

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.ProductEntity
import javax.inject.Inject

class DeleteProductFromFavoriteUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<ProductEntity, Unit>() {
    override suspend fun execute(params: ProductEntity): Unit =
        productRepository.deleteFromFavorites(params)
}