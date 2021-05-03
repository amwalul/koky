package com.amwa.koky.data.remote.api.model.recipe


import com.google.gson.annotations.SerializedName

data class RecipeListResultResponse(
    @SerializedName("title")
    val title: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("times")
    val times: String,
    @SerializedName("portion", alternate = ["serving"])
    val portion: String,
    @SerializedName("dificulty", alternate = ["difficulty"])
    val dificulty: String
)