package com.amwa.koky.data.remote.api.model.recipe


import com.google.gson.annotations.SerializedName

data class AuthorResponse(
    @SerializedName("user")
    val user: String,
    @SerializedName("datePublished")
    val datePublished: String
)