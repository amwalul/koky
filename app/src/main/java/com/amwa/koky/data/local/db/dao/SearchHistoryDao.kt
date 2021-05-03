package com.amwa.koky.data.local.db.dao

import androidx.room.*
import com.amwa.koky.data.local.db.model.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Query("SELECT * FROM search_history")
    fun getAllHistories(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(historyEntity: SearchHistoryEntity)

    @Delete
    suspend fun deleteHistory(historyEntity: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun deleteAllHistories()
}