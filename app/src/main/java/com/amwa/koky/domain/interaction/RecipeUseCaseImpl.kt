package com.amwa.koky.domain.interaction

import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.domain.model.SearchHistory
import com.amwa.koky.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RecipeUseCaseImpl @Inject constructor(private val recipeRepository: RecipeRepository) :
    RecipeUseCase {
    override fun getNewRecipes() = recipeRepository.getNewRecipes()

    override fun getRecipeDetail(key: String) = recipeRepository.getRecipeDetail(key)

    override fun getCategories() = recipeRepository.getCategories()

    override fun getRecipesByCategory(key: String) = recipeRepository.getRecipesByCategory(key)

    override fun searchRecipe(query: String) = recipeRepository.searchRecipe(query)

    override fun getFavoriteRecipes() = recipeRepository.getFavoriteRecipes()

    override suspend fun getFavoriteRecipeDetail(key: String) =
        recipeRepository.getFavoriteRecipeDetail(key)

    override suspend fun saveRecipe(recipe: Recipe) = recipeRepository.saveRecipe(recipe)

    override suspend fun deleteRecipe(recipe: Recipe) = recipeRepository.deleteRecipe(recipe)

    override fun getAllHistories(): Flow<List<SearchHistory>> = recipeRepository.getAllHistories()

    override suspend fun saveHistory(searchHistory: SearchHistory) =
        recipeRepository.saveHistory(searchHistory)

    override suspend fun deleteHistory(searchHistory: SearchHistory) =
        recipeRepository.deleteHistory(searchHistory)

    override suspend fun deleteAllHistories() = recipeRepository.deleteAllHistories()
}