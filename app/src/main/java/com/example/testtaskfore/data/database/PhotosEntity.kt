package com.example.testtaskfore.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testtaskfore.data.model.Links
import com.example.testtaskfore.data.model.Urls
import com.example.testtaskfore.data.model.User

@Entity(tableName = "photos_database")
data class PhotosEntity(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "blur_hash")
    val blurHash: String,
    val color: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
   /* @ColumnInfo(name = "current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>,*/
    val description: String,
    val height: Int,
    @ColumnInfo(name = "liked_by_user")
    val likedByUser: Boolean,
    val likes: Int,
    val links: Links,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String,
    val urls: Urls,
    val user: User,
    val width: Int
)
