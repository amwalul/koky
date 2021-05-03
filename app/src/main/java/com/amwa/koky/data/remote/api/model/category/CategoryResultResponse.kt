package com.amwa.koky.data.remote.api.model.category


import com.google.gson.annotations.SerializedName

data class CategoryResultResponse(
    @SerializedName("category")
    val category: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("key")
    val key: String
)