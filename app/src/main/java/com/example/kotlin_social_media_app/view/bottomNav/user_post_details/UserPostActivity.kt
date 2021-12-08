package com.example.kotlin_social_media_app.view.bottomNav.user_post_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R

class UserPostActivity : AppCompatActivity() {
    private lateinit var ivPicturePost: ImageView
    private lateinit var ivProfilePicture: ImageView
    private lateinit var tvInstagramName: TextView
    private lateinit var tvDescriptionPost: TextView
    private lateinit var tvLikePost: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_post)

        ivPicturePost = findViewById(R.id.ivPicturePost)
        ivProfilePicture = findViewById(R.id.ivProfilePicture)
        tvInstagramName = findViewById(R.id.tvInstagramName)
        tvDescriptionPost = findViewById(R.id.tvDescriptionPost)
        tvLikePost = findViewById(R.id.tvLikePost)

        val image_url = intent.getStringExtra("image_url")
        val name_user = intent.getStringExtra("name_user")
        val image_post = intent.getStringExtra("image_post")
        val like_post = intent.getStringExtra("like_post")
        val description_post = intent.getStringExtra("description_post")

        tvInstagramName.text = name_user
        tvDescriptionPost.text = description_post
        tvLikePost.text = like_post + " Like"
        Glide.with(ivPicturePost).load(image_post).into(ivPicturePost)
        Glide.with(ivProfilePicture).load(image_url).circleCrop().into(ivProfilePicture)
    }
}