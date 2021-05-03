package com.amwa.koky.data.local.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    val query: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)