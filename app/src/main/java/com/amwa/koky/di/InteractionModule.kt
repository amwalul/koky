package com.amwa.koky.di

import com.amwa.koky.domain.interaction.RecipeUseCase
import com.amwa.koky.domain.interaction.RecipeUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
interface InteractionModule {
    @Binds
    fun provideRecipeUseCase(recipeUseCaseImpl: RecipeUseCaseImpl): RecipeUseCase
}