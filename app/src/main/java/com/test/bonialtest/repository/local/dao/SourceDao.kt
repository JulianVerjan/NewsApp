package com.test.bonialtest.repository.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.test.bonialtest.repository.local.utils.Constants.SOURCE_TABLE
import com.test.bonialtest.repository.model.Source

@Dao
interface SourceDao {

    @Query("SELECT * FROM $SOURCE_TABLE")
    fun getAllNewsSource(): List<Source>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(source: List<Source>)
}