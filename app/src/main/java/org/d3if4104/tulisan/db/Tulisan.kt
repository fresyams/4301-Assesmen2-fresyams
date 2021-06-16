package org.d3if4104.tulisan.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diary")
data class Tulisan(
        @PrimaryKey(autoGenerate = true)
        var id:Long = 0L,

        @ColumnInfo(name = "message")
        var message:String,

        @ColumnInfo(name = "tanggal")
        val tanggal:Long = System.currentTimeMillis()
)