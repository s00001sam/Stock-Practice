package com.sam.stockassignment.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sam.stockassignment.data.StockLocalData

@Database(
    entities = [StockLocalData::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDB: RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var instance: RoomDB? = null
        private val LOCK = Any()

        fun getInstance(context: Context) = instance
            ?: synchronized(LOCK) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                RoomDB::class.java,
                "stock_room_db"
            ).fallbackToDestructiveMigration()
                .build()
    }
}