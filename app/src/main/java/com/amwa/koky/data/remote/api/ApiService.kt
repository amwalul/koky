package com.amwa.koky.data.remote.api

import com.amwa.koky.data.remote.api.model.category.CategoryResponse
import com.amwa.koky.data.remote.api.model.recipe.RecipeDetailResponse
import com.amwa.koky.data.remote.api.model.recipe.RecipeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("recipes")
    suspend fun getNewRecipes(): RecipeListResponse

    @GET("recipe/{key}")
    suspend fun getRecipeDetail(@Path("key") key: String): RecipeDetailResponse

    @GET("categorys/recipes")
    suspend fun getCategories(): CategoryResponse

    @GET("categorys/recipes/{key}")
    suspend fun getRecipesByCategory(@Path("key") key: String): RecipeListResponse

    @GET("search")
    suspend fun searchRecipe(@Query("q") query: String): RecipeListResponse
}