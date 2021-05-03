package com.amwa.koky.data.remote

import com.amwa.koky.data.remote.api.ApiResponse
import com.amwa.koky.data.remote.api.ApiService
import com.amwa.koky.data.remote.api.model.category.CategoryResultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    fun getNewRecipes() = flow {
        try {
            val response = apiService.getNewRecipes()
            val data = response.results
            emit(ApiResponse.Success(data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getRecipeDetail(key: String) = flow {
        try {
            val response = apiService.getRecipeDetail(key)
            val data = response.result
            emit(ApiResponse.Success(data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getCategories() = flow {
        try {
            val response = apiService.getCategories()
            val data = response.results.toMutableList().apply {
                add(CategoryResultResponse("Resep Terbaru", "", "new"))
            }.toList().asReversed()
            emit(ApiResponse.Success(data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getRecipesByCategory(key: String) = flow {
        try {
            val response = apiService.getRecipesByCategory(key)
            val data = response.results
            emit(ApiResponse.Success(data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun searchRecipe(query: String) = flow {
        try {
            val response = apiService.searchRecipe(query)
            val data = response.results
            emit(ApiResponse.Success(data))
        } catch (e: Exception) {
            emit(ApiResponse.Error(e.toString()))
        }
    }.flowOn(Dispatchers.IO)
}