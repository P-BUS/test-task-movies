package com.example.testtaskfore.data.model

import com.squareup.moshi.Json

data class UnsplashPhoto(
    @Json(name = "blur_hash")
    val blurHash: String = "",
    val color: String = "",
    @Json(name = "created_at")
    val createdAt: String = "",
  /*  @Json(name = "current_user_collections")
    val currentUserCollections: List<CurrentUserCollection>,*/
    val description: String = "",
    val height: Int = 0,
    val id: String = "",
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    val likes: Int = 0,
    //val links: Links,
    @Json(name = "updated_at")
    val updatedAt: String = "",
    val urls: Urls,
    //val user: User,
    val width: Int = 0
)

/*data class CurrentUserCollection(
    @Json(name = "cover_photo")
    val coverPhoto: Any,
    val id: Int,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String,
    @Json(name = "published_at")
    val publishedAt: String,
    val title: String,
    @Json(name = "updated_at") val
    updatedAt: String,
    val user: Any
)*/

/*data class Links(
    val download: String = "",
    @Json(name = "download_location")
    val downloadLocation: String = "",
    val html: String = "",
    val self: String = ""
)*/

data class Urls(
    val full: String = "",
    val raw: String = "",
    val regular: String = "",
    val small: String = "",
    val thumb: String = ""
)

data class User(
    val bio: String = "",
    val id: String = "",
    @Json(name = "instagram_username") val instagram_username: String = "",
    val links: LinksX,
    val location: String = "",
    val name: String = "",
    @Json(name = "portfolio_url")
    val portfolioUrl: String = "",
    @Json(name = "profile_image")
    val profileImage: ProfileImage,
    @Json(name = "total_collections")
    val totalCollections: Int = 0,
    @Json(name = "total_likes")
    val totalLikes: Int = 0,
    @Json(name = "total_photos")
    val totalPhotos: Int = 0,
    @Json(name = "twitter_username")
    val twitterUsername: String = "",
    val username: String = ""
)

data class LinksX(
    val html: String = "",
    val likes: String = "",
    val photos: String = "",
    val portfolio: String = "",
    val self: String = ""
)

data class ProfileImage(
    val large: String = "",
    val medium: String = "",
    val small: String = ""
)