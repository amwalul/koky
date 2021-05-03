package com.amwa.koky.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @PrimaryKey
    val key: String,
    val title: String,
    val thumb: String?,
    val servings: String,
    val times: String,
    val dificulty: String,
    val desc: String,
    val ingredient: List<String>,
    val step: List<String>
)