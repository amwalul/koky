package com.amwa.koky.di

import android.content.Context
import com.amwa.koky.data.local.db.RecipeDatabase
import com.amwa.koky.data.local.db.dao.RecipeDao
import com.amwa.koky.data.local.db.dao.SearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): RecipeDatabase =
        RecipeDatabase.create(context)

    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao = database.recipeDao()

    @Provides
    fun provideSearchHistoryDao(database: RecipeDatabase): SearchHistoryDao =
        database.searchHistoryDao()
}