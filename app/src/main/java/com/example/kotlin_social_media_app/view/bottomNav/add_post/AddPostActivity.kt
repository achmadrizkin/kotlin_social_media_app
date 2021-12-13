package com.example.kotlin_social_media_app.view.bottomNav.add_post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.user.User
import com.example.kotlin_social_media_app.view_model.AddPostActivityViewModel
import com.example.kotlin_social_media_app.view_model.EditProfileActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddPostActivity : AppCompatActivity() {
    private lateinit var viewModel: AddPostActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        //
        val name_user = intent.getStringExtra("name_user")
        val image_url = intent.getStringExtra("image_url")
        val email_user = intent.getStringExtra("email_user")

        val btnPost = findViewById<Button>(R.id.btnPost)
        val etDescription = findViewById<EditText>(R.id.etDescription)
        val etImagePost = findViewById<EditText>(R.id.etImagePost)
        val tvNone = findViewById<TextView>(R.id.tvNone)

        iniViewModel()
        postExploreObservable()

        if (image_url == "" || image_url!!.isEmpty() || name_user == "" || name_user!!.isEmpty() || image_url.length < 10) {
            btnPost.visibility = View.GONE
            tvNone.visibility = View.VISIBLE
        } else if(etDescription.text.length < 2 || etImagePost.text.length < 2) {
            btnPost.visibility = View.GONE
        }
        else {
            btnPost.visibility = View.VISIBLE
            tvNone.visibility = View.GONE
        }

        btnPost.setOnClickListener {
            val explore = Explore("", name_user!!, email_user!!, image_url!!, etImagePost.text.toString(), etDescription.text.toString(), 0)
            postExplore(explore)
        }
    }

    private fun iniViewModel() {
        viewModel = ViewModelProvider(this).get(AddPostActivityViewModel::class.java)
    }

    private fun postExploreObservable() {
        viewModel.postExploreObservable().observe(this, Observer {
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

    private fun postExplore(explore: Explore) {
        viewModel.postExploreOfData(explore)
    }
}