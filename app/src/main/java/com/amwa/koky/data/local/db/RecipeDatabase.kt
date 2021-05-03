package com.amwa.koky.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amwa.koky.data.local.db.converter.StringTypeConverter
import com.amwa.koky.data.local.db.dao.RecipeDao
import com.amwa.koky.data.local.db.dao.SearchHistoryDao
import com.amwa.koky.data.local.db.model.RecipeEntity
import com.amwa.koky.data.local.db.model.SearchHistoryEntity

@Database(
    entities = [RecipeEntity::class, SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(StringTypeConverter::class)
abstract class RecipeDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipeDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    companion object {
        fun create(context: Context): RecipeDatabase =
            Room.databaseBuilder(context, RecipeDatabase::class.java, "Recipe.db")
                .fallbackToDestructiveMigration().build()
    }
}