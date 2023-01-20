package com.example.testtaskfore.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtaskfore.data.model.Urls
import com.example.testtaskfore.data.model.User

@Entity(tableName = "photos_database")
data class PhotosEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    val description: String?,
    @ColumnInfo(name = "alt_description")
    val altDescription: String?,
    @ColumnInfo(name = "liked_by_user")
    val likedByUser: Boolean?,
    val likes: Int,
    val urls: Urls,
    val user: User
)
