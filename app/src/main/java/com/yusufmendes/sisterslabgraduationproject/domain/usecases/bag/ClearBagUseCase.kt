package com.yusufmendes.sisterslabgraduationproject.domain.usecases.bag

import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.model.ProductX
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class ClearBagUseCase @Inject constructor(
    private val deleteToProductFromBagUseCase: DeleteToProductFromBagUseCase
) :
    SuspendUseCase<List<ProductX>, Boolean>() {
    override suspend fun execute(items: List<ProductX>): Boolean {
        supervisorScope {
            val results = items.map {
                async {
                    deleteToProductFromBagUseCase.invoke(it.id)
                }
            }.awaitAll()
        }
        return true
    }
}