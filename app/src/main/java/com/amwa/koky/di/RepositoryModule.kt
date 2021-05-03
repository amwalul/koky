package com.amwa.koky.di

import com.amwa.koky.data.RecipeRepositoryImpl
import com.amwa.koky.domain.repository.RecipeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun provideRecipeRepository(recipeRepositoryImpl: RecipeRepositoryImpl): RecipeRepository
}