package com.example.testtaskfore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtaskfore.utils.ConverterUrls

@Database(entities = [PhotosEntity::class], version = 1, exportSchema = false)
@TypeConverters(ConverterUrls::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}