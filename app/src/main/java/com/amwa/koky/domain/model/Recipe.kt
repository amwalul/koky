package com.amwa.koky.domain.model

data class Recipe(
    var key: String?,
    val title: String,
    var thumb: String?,
    val servings: String,
    val times: String,
    val dificulty: String,
    val desc: String?,
    val ingredient: List<String>?,
    val step: List<String>?
)