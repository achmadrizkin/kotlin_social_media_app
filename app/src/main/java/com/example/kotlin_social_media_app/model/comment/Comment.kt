package com.example.kotlin_social_media_app.model.comment

data class Comment(
    val id: String,
    val name_user: String,
    val email_user: String,
    val image_url: String,

    val comment: String,
    val to_user: String,
    val post_user: String,

    val create_at: String,
    val update_at: String,
)