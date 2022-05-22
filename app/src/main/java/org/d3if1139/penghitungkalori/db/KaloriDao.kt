package org.d3if1139.penghitungkalori.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface KaloriDao {
    @Insert
    fun insert(kalori: KaloriEntity)

    @Query("SELECT * FROM kalori ORDER BY id DESC")
    fun getLastKalori(): LiveData<List<KaloriEntity?>>
    @Query("DELETE FROM kalori")
    fun clearData()
}