package org.d3if4104.tulisan.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TulisanDAO {
    @Insert
    fun insert(diary: Tulisan)

    @Update
    fun update(diary: Tulisan)

    @Query("SELECT * FROM diary")
    fun getDiary(): LiveData<List<Tulisan>>

    @Query("DELETE FROM diary")
    fun clear()

    @Query("DELETE FROM diary WHERE id = :diaryId")
    fun delete(diaryId: Long)
}