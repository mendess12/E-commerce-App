package com.yusufmendes.sisterslabgraduationproject.data.di

import com.yusufmendes.sisterslabgraduationproject.domain.repos.ProductRepository
import com.yusufmendes.sisterslabgraduationproject.data.repository.ProductRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindings {

    @Singleton
    @Binds
    abstract fun bindProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository
}