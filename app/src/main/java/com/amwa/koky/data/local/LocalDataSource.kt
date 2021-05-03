package com.amwa.koky.data.local

import com.amwa.koky.data.local.db.dao.RecipeDao
import com.amwa.koky.data.local.db.dao.SearchHistoryDao
import com.amwa.koky.data.local.db.model.RecipeEntity
import com.amwa.koky.data.local.db.model.SearchHistoryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val recipeDao: RecipeDao,
    private val searchHistoryDao: SearchHistoryDao
) {

    fun getAllRecipes() = recipeDao.getAllRecipes().flowOn(Dispatchers.IO)

    suspend fun getRecipe(key: String) = withContext(Dispatchers.IO) {
        recipeDao.getRecipe(key)
    }

    suspend fun insertRecipe(recipe: RecipeEntity) = withContext(Dispatchers.IO) {
        recipeDao.insertRecipe(recipe)
    }

    suspend fun deleteRecipe(recipe: RecipeEntity) = withContext(Dispatchers.IO) {
        recipeDao.deleteRecipe(recipe)
    }

    fun getAllHistories() = searchHistoryDao.getAllHistories().flowOn(Dispatchers.IO)

    suspend fun insertHistory(historyEntity: SearchHistoryEntity) = withContext(Dispatchers.IO) {
        searchHistoryDao.insertHistory(historyEntity)
    }

    suspend fun deleteHistory(historyEntity: SearchHistoryEntity) = withContext(Dispatchers.IO) {
        searchHistoryDao.deleteHistory(historyEntity)
    }

    suspend fun deleteAllHistories() = withContext(Dispatchers.IO) {
        searchHistoryDao.deleteAllHistories()
    }
}