package com.amwa.koky.data.remote.api.model.recipe


import com.google.gson.annotations.SerializedName

data class NeedItemResponse(
    @SerializedName("item_name")
    val itemName: String,
    @SerializedName("thumb_item")
    val thumbItem: String
)