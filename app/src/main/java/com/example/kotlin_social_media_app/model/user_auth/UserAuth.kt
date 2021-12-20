package com.example.kotlin_social_media_app.model.user_auth

data class UserAuth (
    val email_user: String
)

data class UpdateUserPost (
    val email_user: String,
    val id: Int,
)