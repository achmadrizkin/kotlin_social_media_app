package com.example.kotlin_social_media_app.model.user

data class User (
    val name_user: String,
    val email_user: String,
    val image_url: String,
    val following: Int,
    val followers: Int,
    val post: Int,
)
