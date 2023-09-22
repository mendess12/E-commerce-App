package com.yusufmendes.sisterslabgraduationproject.di

import com.yusufmendes.sisterslabgraduationproject.services.ProductAPI
import com.yusufmendes.sisterslabgraduationproject.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideProductAPI(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    @Provides
    @Singleton
    fun provideMovieAppService(retrofit: Retrofit): ProductAPI {
        return retrofit.create(ProductAPI::class.java)
    }

}