package com.example.kotlin_social_media_app.view.bottomNav.comment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.CommentAdapter
import com.example.kotlin_social_media_app.adapter.SearchUserAdapter
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view.bottomNav.post_details.PostDetailsActivity
import com.example.kotlin_social_media_app.view.bottomNav.user_post_details.UserPostActivity
import com.example.kotlin_social_media_app.view_model.CommentActivityViewModel
import com.example.kotlin_social_media_app.view_model.SearchActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class CommentActivity : AppCompatActivity() {
    lateinit var commentAdapter: CommentAdapter
    lateinit var recyclerViewComment: RecyclerView

    //
    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        recyclerViewComment = findViewById(R.id.recyclerViewComment)
        val tvNameUser = findViewById<TextView>(R.id.tvNameUser)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val ivProfilePicture = findViewById<ImageView>(R.id.ivProfilePicture)
        val ivBack = findViewById<ImageView>(R.id.ivBack)

        disposables = CompositeDisposable()

        val name = intent.getStringExtra("name")
        val description_post = intent.getStringExtra("description_post")
        val image_url = intent.getStringExtra("image_url")
        val id = intent.getStringExtra("id")

        //
        Glide.with(ivProfilePicture).load(image_url).circleCrop().into(ivProfilePicture)

        tvNameUser.text = name
        tvDescription.text = description_post

        //
        initCommentRecyclerView()
        getCommentApiData(description_post!!, id!!)

        ivBack.setOnClickListener {
            val i = Intent(this, BottomNavActivity::class.java)
            startActivity(i)
        }
    }

    private fun initCommentRecyclerView() {
        recyclerViewComment = findViewById<RecyclerView>(R.id.recyclerViewComment)
        recyclerViewComment.apply {
            layoutManager = LinearLayoutManager(this@CommentActivity)

            //
            commentAdapter = CommentAdapter()
            adapter = commentAdapter
        }
    }

    private fun getCommentApiData(postUser: String, toId: String) {
        val viewModel = ViewModelProvider(this).get(CommentActivityViewModel::class.java)
        viewModel.getCommentObservable().observe(this, Observer {
            if (it != null) {
                commentAdapter.setCommentList(it.data)
                commentAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getCommentListOfData(postUser, toId)
    }


    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }
}