package org.d3if1139.penghitungkalori.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [KaloriEntity::class], version = 1, exportSchema = false)
abstract class Kalori: RoomDatabase(){
    abstract val dao: KaloriDao
    companion object {
        @Volatile
        private var INSTANCE: Kalori? = null
        fun getInstance(context: Context): Kalori{
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        Kalori::class.java,
                        "kalori.db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}