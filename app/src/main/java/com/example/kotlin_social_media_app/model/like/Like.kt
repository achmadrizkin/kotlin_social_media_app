package com.example.kotlin_social_media_app.model.like

data class Like (
    val email_user: String,
    val to_id: String,
    val post_user: String,
    val is_like: String
)