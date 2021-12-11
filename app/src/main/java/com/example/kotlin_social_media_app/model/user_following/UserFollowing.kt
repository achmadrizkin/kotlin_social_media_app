package com.example.kotlin_social_media_app.model.user_following

data class UserFollowing(
    val id: String,
    val user_id: String,
    val name_user: String,
    val email_user: String,
    val image_url: String
)