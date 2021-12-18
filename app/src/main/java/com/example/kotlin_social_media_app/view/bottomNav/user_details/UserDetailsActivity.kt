package com.example.kotlin_social_media_app.view.bottomNav.user_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view.bottomNav.user_post_details.UserPostActivity
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity(),  ExploreAdapter.OnItemClickListener {
    lateinit var exploreAdapter: ExploreAdapter
    lateinit var recyclerViewUserDetailsLayout : RecyclerView
    private lateinit var mAuth: FirebaseAuth

    //
    private lateinit var ivImageUrl: ImageView
    private lateinit var ivBack: ImageView
    private lateinit var ivEmpty: ImageView

    private lateinit var tvUserNameHeader: TextView
    private lateinit var tvUserName: TextView
    private lateinit var tvEmpty: TextView

    private lateinit var tvPostCount: TextView
    private lateinit var tvFollowersCount: TextView
    private lateinit var tvFollowingCount: TextView

    private lateinit var buttonFollow: Button
    private lateinit var buttonMessage: Button
    private lateinit var buttonEditProfile: Button

    var email_user: String = "null"

    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

        //
        disposables = CompositeDisposable()

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        //
        val email = intent.getStringExtra("email")

        //
        recyclerViewUserDetailsLayout = findViewById(R.id.searchViewUserDetails)
        ivImageUrl = findViewById(R.id.ivImageUrl)
        tvUserNameHeader = findViewById(R.id.tvUserNameHeader)
        tvUserName = findViewById(R.id.tvUserName)
        tvPostCount = findViewById(R.id.tvPostCount)
        tvFollowersCount = findViewById(R.id.tvFollowersCount)
        tvFollowingCount = findViewById(R.id.tvFollowingCount)
        ivBack = findViewById(R.id.ivBack)
        ivEmpty = findViewById(R.id.ivEmpty)
        tvEmpty = findViewById(R.id.tvEmpty)
        buttonFollow = findViewById(R.id.buttonFollow)
        buttonMessage = findViewById(R.id.buttonMessage)
        buttonEditProfile = findViewById(R.id.buttonEditProfile)

        ivBack.setOnClickListener {
            val signInIntent = Intent(this, BottomNavActivity::class.java)
            startActivity(signInIntent)
        }

        if (currentUser?.email!! == email) {
            buttonFollow.visibility = View.GONE
            buttonMessage.visibility = View.GONE
            buttonEditProfile.visibility = View.VISIBLE
        } else {
            buttonFollow.visibility = View.VISIBLE
            buttonMessage.visibility = View.VISIBLE
            buttonEditProfile.visibility = View.GONE
        }

        //
        initExploreRecyclerView()
        if (email != null) {
            getUserByEmail(email)
            getExploreByEmailApiData(email)
        }
    }

    private fun initExploreRecyclerView() {
        recyclerViewUserDetailsLayout = findViewById<RecyclerView>(R.id.searchViewUserDetails)
        recyclerViewUserDetailsLayout.apply {
            layoutManager = GridLayoutManager(this@UserDetailsActivity, 3)

            exploreAdapter = ExploreAdapter(this@UserDetailsActivity)
            adapter = exploreAdapter
        }
    }

    private fun getUserByEmail(email: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getUserByEmailObservable().observe(this, Observer {
            if (it != null) {
                tvUserName.setText(it.data[0].name_user)
                tvUserNameHeader.setText(it.data[0].name_user)

                // image
                if (it.data[0].image_url.isNotEmpty()) {
                    Glide.with(ivImageUrl).load(it.data[0].image_url).circleCrop().into(ivImageUrl)
                }

                //
                email_user = it.data[0].email_user


                //
                tvPostCount.setText(it.data[0].post.toString())
                tvFollowersCount.setText(it.data[0].followers.toString())
                tvFollowingCount.setText(it.data[0].following.toString())
            } else {
                recyclerViewUserDetailsLayout.visibility = View.GONE
            }
        })
        viewModel.getUserListByEmailOfData(email)
    }

    private fun getExploreByEmailApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(this, Observer {
            if (it != null) {
                exploreAdapter.setExploreList(it.data)
                exploreAdapter.notifyDataSetChanged()

                ivEmpty.visibility = View.GONE
                tvEmpty.visibility = View.GONE
            } else {
                recyclerViewUserDetailsLayout.visibility = View.GONE

                ivEmpty.visibility = View.VISIBLE
                tvEmpty.visibility = View.VISIBLE
            }
        })
        viewModel.getExploreListOfData(input)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables!!.dispose()
    }

    override fun onItemClickListenerExplore(explore: Explore, position: Int) {
        val i = Intent(this, UserPostActivity::class.java)

        i.putExtra("position", position.toString())
        i.putExtra("email", email_user)

        startActivity(i)
    }
}