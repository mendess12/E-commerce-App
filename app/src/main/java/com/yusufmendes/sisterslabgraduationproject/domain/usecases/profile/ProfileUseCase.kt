package com.yusufmendes.sisterslabgraduationproject.domain.usecases.profile

import com.yusufmendes.sisterslabgraduationproject.domain.AppResult
import com.yusufmendes.sisterslabgraduationproject.domain.SuspendUseCase
import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.model.UserResponse
import javax.inject.Inject

class ProfileUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : SuspendUseCase<UseParams, AppResult<UserResponse>>() {
    override suspend fun execute(params: UseParams): AppResult<UserResponse> =
        productRepository.getUser(params.userId)
}

data class UseParams(val userId: String)