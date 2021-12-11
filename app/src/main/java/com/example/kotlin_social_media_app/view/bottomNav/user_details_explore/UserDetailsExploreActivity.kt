package com.example.kotlin_social_media_app.view.bottomNav.user_details_explore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.view.auth.SignInActivity
import com.example.kotlin_social_media_app.view.bottomNav.following.FollowingActivity
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class UserDetailsExploreActivity : AppCompatActivity() {
    //
    private lateinit var ivImageUrl: ImageView
    private lateinit var ivEmpty: ImageView

    private lateinit var tvUserNameHeader: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmpty: TextView
    private lateinit var imguserUrl: String

    private lateinit var buttonEditProfile: Button

    private lateinit var tvPostCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView

    var disposables: CompositeDisposable? = null

    private lateinit var ivExit: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details_explore)

        ivImageUrl = findViewById(R.id.ivImageUrl)
        tvUserNameHeader = findViewById(R.id.tvUserNameHeader)
        tvUserName = findViewById(R.id.tvUserName)
        tvPostCount = findViewById(R.id.tvPostCount)
        tvFollowersCount = findViewById(R.id.tvFollowingCount1)
        tvFollowingCount = findViewById(R.id.tvFollowingCount1)
        ivEmpty = findViewById(R.id.ivEmpty)
        tvEmpty = findViewById(R.id.tvEmpty)

        val email = intent.getStringExtra("email")

        if (email != null || email == "") {
            getUserByEmail(email)
        }
    }

    private fun getUserByEmail(email: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getUserByEmailObservable().observe(this, Observer {
            if (it != null) {
                if (it.data[0].name_user.isEmpty() || it.data[0].name_user == "" || it.data[0].image_url.isEmpty() || it.data[0].image_url == "") {
                    tvUserName.setText("Anonymous")
                    tvUserNameHeader.setText("Anonymous")
                } else {
                    tvUserName.setText(it.data[0].name_user)
                    tvUserNameHeader.setText(it.data[0].name_user)

                    // image
                    Glide.with(ivImageUrl).load(it.data[0].image_url).circleCrop().into(ivImageUrl)
                }

                //
                tvPostCount.setText(it.data[0].post.toString())
                tvFollowersCount.setText(it.data[0].followers.toString())
                tvFollowingCount.setText(it.data[0].following.toString())
            }
        })
        viewModel.getUserListByEmailOfData(email)
    }

}