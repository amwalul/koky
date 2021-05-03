package com.amwa.koky.data.remote.api.model.recipe


import com.google.gson.annotations.SerializedName

data class RecipeDetailResultResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("thumb")
    val thumb: String?,
    @SerializedName("servings")
    val servings: String,
    @SerializedName("times")
    val times: String,
    @SerializedName("dificulty")
    val dificulty: String,
    @SerializedName("author")
    val author: AuthorResponse,
    @SerializedName("desc")
    val desc: String,
    @SerializedName("needItem")
    val needItem: List<NeedItemResponse>,
    @SerializedName("ingredient")
    val ingredient: List<String>,
    @SerializedName("step")
    val step: List<String>
)