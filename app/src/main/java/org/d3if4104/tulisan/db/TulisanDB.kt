package org.d3if4104.tulisan.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Tulisan::class], version = 1, exportSchema = false)
abstract class TulisanDB : RoomDatabase(){
    abstract val TulisanDAO: TulisanDAO

    companion object {
        @Volatile
        private var INSTANCE: TulisanDB? = null

        fun getInstance(context: Context): TulisanDB {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TulisanDB::class.java,
                        "tulisan_database"
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