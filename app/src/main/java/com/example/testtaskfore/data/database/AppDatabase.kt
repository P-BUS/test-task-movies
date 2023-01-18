package com.example.testtaskfore.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PhotosEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photosDao(): PhotosDao
}