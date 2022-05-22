package org.d3if1139.penghitungkalori.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kalori")
data class KaloriEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    var tanggal: Long = System.currentTimeMillis(),
    var berat: Double,
    var tinggi: Double,
    var usia: Double,
    var isMale: Boolean
)
