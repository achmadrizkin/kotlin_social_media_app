package com.example.kotlin_social_media_app.view.bottomNav.following

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.adapter.FollowingAdapter
import com.example.kotlin_social_media_app.adapter.SearchUserAdapter
import com.example.kotlin_social_media_app.view_model.FollowingActivityViewModel
import com.example.kotlin_social_media_app.view_model.PostDetailsActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class FollowingActivity : AppCompatActivity() {
    private lateinit var recyclerViewFollowing: RecyclerView
    private lateinit var followingAdapter: FollowingAdapter
    private lateinit var ivBack: ImageView

    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_following)

        //
        disposables = CompositeDisposable()

        //
        val email = intent.getStringExtra("email")

        //
        recyclerViewFollowing = findViewById(R.id.recyclerViewFollowing)
        ivBack = findViewById(R.id.ivBack)

        //
        initSearchRecyclerView()
        getFollowingByUser(email!!)

        //
        ivBack.setOnClickListener {

        }
    }

    private fun initSearchRecyclerView() {
        recyclerViewFollowing = findViewById<RecyclerView>(R.id.recyclerViewFollowing)
        recyclerViewFollowing.apply {
            layoutManager = LinearLayoutManager(this@FollowingActivity)

            //
            followingAdapter = FollowingAdapter()
            adapter = followingAdapter
        }
    }

    private fun getFollowingByUser(input: String) {
        val viewModel = ViewModelProvider(this).get(FollowingActivityViewModel::class.java)
        viewModel.getFollowingByUserObservable().observe(this, Observer {
            if (it != null) {
                followingAdapter.setFollowingList(it.data)
                followingAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getFollowingByUserOfData(input)
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

}