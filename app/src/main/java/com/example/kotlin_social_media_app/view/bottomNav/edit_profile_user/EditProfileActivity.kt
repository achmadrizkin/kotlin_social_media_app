package com.example.kotlin_social_media_app.view.bottomNav.edit_profile_user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.user.User
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view.bottomNav.user_post_details.UserPostActivity
import com.example.kotlin_social_media_app.view_model.EditProfileActivityViewModel
import com.example.kotlin_social_media_app.view_model.SignInActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileActivity : AppCompatActivity() {
    private lateinit var viewModelEditProfile: EditProfileActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        val id = intent.getStringExtra("id")
        val name_user = intent.getStringExtra("name_user")
        val image_url = intent.getStringExtra("image_url")
        val email_user = intent.getStringExtra("email_user")
        val following = intent.getStringExtra("following")
        val followers = intent.getStringExtra("followers")
        val post = intent.getStringExtra("post")


        val etImageUrl = findViewById<EditText>(R.id.etImageUrl)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val ivProfilePicture = findViewById<ImageView>(R.id.ivProfilePicture)
        val btnUpdateProfile = findViewById<Button>(R.id.btnUpdateProfile)
        val ivBack = findViewById<ImageView>(R.id.ivBack)

        etImageUrl.setText(image_url)
        etUsername.setText(name_user)
        if (image_url != "" || image_url!!.isNotEmpty()) {
            Glide.with(ivProfilePicture).load(image_url).circleCrop().into(ivProfilePicture)
        }

        //
        iniViewModel()
        updateUserProfileObservable()

        //
        btnUpdateProfile.setOnClickListener {
            val user = User(id!!, etUsername.text.toString(), email_user!!, etImageUrl.text.toString(), following!!.toInt(), followers!!.toInt(), post!!.toInt())
            updateUserProfile(id!!, user)
        }

        ivBack.setOnClickListener {
            val i = Intent(this, BottomNavActivity::class.java)
            startActivity(i)
        }
    }

    private fun iniViewModel() {
        viewModelEditProfile = ViewModelProvider(this).get(EditProfileActivityViewModel::class.java)
    }

    private fun updateUserProfileObservable() {
        viewModelEditProfile.updateUserProfileObservable().observe(this, Observer {
            if (it == null) {
                Toast.makeText(
                    this,
                    "Failed to create/update new user",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Success to create/update new user",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        })
    }

    private fun updateUserProfile(id: String, user: User) {
        viewModelEditProfile.updateUserProfileOfData(id, user)
    }
}