package com.amwa.koky.data.remote.api.model.recipe


import com.google.gson.annotations.SerializedName

data class RecipeDetailResponse(
    @SerializedName("method")
    val method: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("results")
    val result: RecipeDetailResultResponse
)