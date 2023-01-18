package com.example.testtaskfore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.testtaskfore.utils.ConverterUrls
import com.example.testtaskfore.utils.ConverterUser

@Database(entities = [PhotosEntity::class], version = 1, exportSchema = false)
@TypeConverters(ConverterUrls::class, ConverterUser::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}