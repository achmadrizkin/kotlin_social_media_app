package com.example.kotlin_social_media_app.model.explore

data class Explore(
    val name_user: String,
    val email_user: String,
    val image_url: String,

    val image_post: String,
    val description_post: String,
    val like_post: String,

    val create_at: String,
    val update_at: String,
)