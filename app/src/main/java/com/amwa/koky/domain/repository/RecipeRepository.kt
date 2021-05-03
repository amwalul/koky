package com.amwa.koky.domain.repository

import com.amwa.koky.data.Resource
import com.amwa.koky.domain.model.Category
import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {

    fun getNewRecipes(): Flow<Resource<List<Recipe>>>

    fun getRecipeDetail(key: String): Flow<Resource<Recipe>>

    fun getCategories(): Flow<Resource<List<Category>>>

    fun getRecipesByCategory(key: String): Flow<Resource<List<Recipe>>>

    fun searchRecipe(query: String): Flow<Resource<List<Recipe>>>

    fun getFavoriteRecipes(): Flow<List<Recipe>>

    suspend fun getFavoriteRecipeDetail(key: String): Recipe?

    suspend fun saveRecipe(recipe: Recipe)

    suspend fun deleteRecipe(recipe: Recipe)

    fun getAllHistories(): Flow<List<SearchHistory>>

    suspend fun saveHistory(searchHistory: SearchHistory)

    suspend fun deleteHistory(searchHistory: SearchHistory)

    suspend fun deleteAllHistories()
}