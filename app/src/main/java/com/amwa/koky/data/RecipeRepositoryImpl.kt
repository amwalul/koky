package com.amwa.koky.data

import com.amwa.koky.data.local.LocalDataSource
import com.amwa.koky.data.mapper.*
import com.amwa.koky.data.remote.RemoteDataSource
import com.amwa.koky.data.remote.api.ApiResponse
import com.amwa.koky.domain.model.Recipe
import com.amwa.koky.domain.model.SearchHistory
import com.amwa.koky.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : RecipeRepository {
    override fun getNewRecipes() = flow {
        emit(Loading)

        when (val apiResponse = remoteDataSource.getNewRecipes().first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data.map {
                    RecipeListResponseMapper.mapToDomain(it)
                }
                emit(Success(data))
            }
            is ApiResponse.Error -> {
                emit(Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipeDetail(key: String) = flow {
        emit(Loading)

        when (val apiResponse = remoteDataSource.getRecipeDetail(key).first()) {
            is ApiResponse.Success -> {
                val data = RecipeDetailResponseMapper.mapToDomain(apiResponse.data)
                emit(Success(data))
            }
            is ApiResponse.Error -> {
                emit(Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getCategories() = flow {
        emit(Loading)

        when (val apiResponse = remoteDataSource.getCategories().first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data.map {
                    CategoryResponseMapper.mapToDomain(it)
                }
                emit(Success(data))
            }
            is ApiResponse.Error -> {
                emit(Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getRecipesByCategory(key: String) = flow {
        emit(Loading)

        when (val apiResponse = remoteDataSource.getRecipesByCategory(key).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data.map {
                    RecipeListResponseMapper.mapToDomain(it)
                }
                emit(Success(data))
            }
            is ApiResponse.Error -> {
                emit(Error(apiResponse.errorMessage))
            }
        }
    }

    override fun searchRecipe(query: String) = flow {
        emit(Loading)

        when (val apiResponse = remoteDataSource.searchRecipe(query).first()) {
            is ApiResponse.Success -> {
                val data = apiResponse.data.map {
                    RecipeListResponseMapper.mapToDomain(it)
                }
                emit(Success(data))
            }
            is ApiResponse.Error -> {
                emit(Error(apiResponse.errorMessage))
            }
        }
    }

    override fun getFavoriteRecipes() = localDataSource.getAllRecipes().map { recipeList ->
        recipeList.map { RecipeEntityMapper.mapToDomain(it) }
    }

    override suspend fun getFavoriteRecipeDetail(key: String) =
        localDataSource.getRecipe(key)?.let {
            RecipeEntityMapper.mapToDomain(it)
        }

    override suspend fun saveRecipe(recipe: Recipe) {
        val recipeEntity = RecipeDomainMapper.mapToEntity(recipe)
        localDataSource.insertRecipe(recipeEntity)
    }

    override suspend fun deleteRecipe(recipe: Recipe) {
        val recipeEntity = RecipeDomainMapper.mapToEntity(recipe)
        localDataSource.deleteRecipe(recipeEntity)
    }

    override fun getAllHistories() = localDataSource.getAllHistories().map { historyList ->
        historyList.map { SearchHistoryEntityMapper.mapToDomain(it) }
    }

    override suspend fun saveHistory(searchHistory: SearchHistory) {
        val searchHistoryEntity = SearchHistoryDomainMapper.mapToEntity(searchHistory)
        localDataSource.insertHistory(searchHistoryEntity)
    }

    override suspend fun deleteHistory(searchHistory: SearchHistory) {
        val searchHistoryEntity = SearchHistoryDomainMapper.mapToEntity(searchHistory)
        localDataSource.deleteHistory(searchHistoryEntity)
    }

    override suspend fun deleteAllHistories() = localDataSource.deleteAllHistories()
}