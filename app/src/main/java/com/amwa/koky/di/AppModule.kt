package com.amwa.koky.di

import com.amwa.koky.data.remote.api.ApiService
import com.amwa.koky.data.remote.api.ServiceFactory
import com.amwa.koky.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideApiService(): ApiService = ServiceFactory.makeService(Constants.BASE_URL)
}