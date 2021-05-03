package com.amwa.koky.data.remote.api.model.category


import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("method")
    val method: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("results")
    val results: List<CategoryResultResponse>
)