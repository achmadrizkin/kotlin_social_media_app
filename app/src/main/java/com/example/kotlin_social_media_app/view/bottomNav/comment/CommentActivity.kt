package com.example.kotlin_social_media_app.view.bottomNav.comment

import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.CommentAdapter
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view_model.CommentActivityViewModel
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable


@AndroidEntryPoint
class CommentActivity : AppCompatActivity() {
    lateinit var commentAdapter: CommentAdapter
    lateinit var recyclerViewComment: RecyclerView

    //
    var disposables: CompositeDisposable? = null

    //
    private lateinit var name_user: String
    private lateinit var email_user: String
    private lateinit var img_url: String
    private lateinit var commenttss: String

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        recyclerViewComment = findViewById(R.id.recyclerViewComment)
        val tvNameUser = findViewById<TextView>(R.id.tvNameUser)
        val tvDescription = findViewById<TextView>(R.id.tvDescription)
        val ivProfilePicture = findViewById<ImageView>(R.id.ivProfilePicture)
        val ivBack = findViewById<ImageView>(R.id.ivBack)
        val ivSend = findViewById<ImageView>(R.id.ivSend)
        val etPostComment = findViewById<EditText>(R.id.etPostComment)

        disposables = CompositeDisposable()

        getUserByEmail(currentUser?.email!!)


        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val image_url = intent.getStringExtra("image_url")
        val description_post = intent.getStringExtra("description_post")
        val to_email = intent.getStringExtra("to_email")

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

        ivSend.setOnClickListener {
            commenttss = etPostComment.text.toString()

            // post comment
            postComment(id!!, commenttss, to_email!!, description_post!!)

            //
            etPostComment.setText("")

            //
            getCommentApiData(description_post!!, id!!)
        }
    }

    private fun postComment(id: String, comments: String, to_email:String, description_post:String) {
        val viewModel = ViewModelProvider(this).get(CommentActivityViewModel::class.java)

        val comment = Comment(id, name_user, email_user, img_url, comments, to_email, description_post)
        viewModel.postCommentOfData(comment)
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

    private fun getUserByEmail(email: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getUserByEmailObservable().observe(this, Observer {
            if (it != null) {
                if (it.data[0].name_user.isEmpty() || it.data[0].name_user == "" || it.data[0].image_url.isEmpty() || it.data[0].image_url == "") {

                } else {

                }
                name_user = it.data[0].name_user
                email_user = it.data[0].email_user
                img_url = it.data[0].image_url
            } else {
            }
        })
        viewModel.getUserListByEmailOfData(email)
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