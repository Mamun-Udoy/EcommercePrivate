package com.example.ecommerce.di

import com.example.ecommerce.network.ApiClient
import com.example.ecommerce.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiInterface(): ApiInterface {
        return ApiClient.retrofit.create(ApiInterface::class.java)
    }



}