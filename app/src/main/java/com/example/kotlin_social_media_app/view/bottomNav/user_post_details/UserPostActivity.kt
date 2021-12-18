package com.example.kotlin_social_media_app.view.bottomNav.user_post_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.PostDetailsAdapter
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.view_model.PostDetailsActivityViewModel
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class UserPostActivity : AppCompatActivity(), PostDetailsAdapter.OnItemClickListener {
    lateinit var postDetailsAdapter: PostDetailsAdapter
    lateinit var recyclerViewUserPostDetails: RecyclerView
    private lateinit var mAuth: FirebaseAuth

    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_post)
        disposables = CompositeDisposable()

        //
        val email_user = intent.getStringExtra("email")

        //
        recyclerViewUserPostDetails = findViewById(R.id.recyclerViewUserPostDetails)
        recyclerViewUserPostDetails.visibility = View.GONE // check if dataset is ready to use, if not, then wait until post delayed

        //
        disposables = CompositeDisposable()

        //
        val position = intent.getStringExtra("position")
        if (position != null) {
            Handler().postDelayed(
                Runnable {
                    recyclerViewUserPostDetails.scrollToPosition(position.toInt())
                    recyclerViewUserPostDetails.visibility = View.VISIBLE
                }, 200
            )
        } else {
            recyclerViewUserPostDetails.visibility = View.VISIBLE
        }


        initSearchRecyclerView()
        getExploreApiData(email_user!!)
    }

    private fun initSearchRecyclerView() {
        recyclerViewUserPostDetails = findViewById<RecyclerView>(R.id.recyclerViewUserPostDetails)
        recyclerViewUserPostDetails.apply {
            layoutManager = LinearLayoutManager(this@UserPostActivity)

            //
            postDetailsAdapter = PostDetailsAdapter(this@UserPostActivity)
            adapter = postDetailsAdapter
        }
    }

    private fun getExploreApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(this, Observer {
            if (it != null) {
                postDetailsAdapter.setExploreList(it.data)
                postDetailsAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getExploreListOfData(input)
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    override fun updateLike(
        explore: Explore,
        holder: PostDetailsAdapter.MyViewHolder,
        position: Int
    ) {

    }
}