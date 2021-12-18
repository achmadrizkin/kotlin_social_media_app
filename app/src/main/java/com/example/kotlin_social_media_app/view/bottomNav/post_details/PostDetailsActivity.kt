package com.example.kotlin_social_media_app.view.bottomNav.post_details

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.PostDetailsAdapter
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.like.Like
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view_model.PostDetailsActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class PostDetailsActivity : AppCompatActivity(), PostDetailsAdapter.OnItemClickListener {
    lateinit var postDetailsAdapter: PostDetailsAdapter
    lateinit var recyclerViewPostDetails: RecyclerView

    lateinit var ivBack: ImageView

    //
    private lateinit var mAuth: FirebaseAuth
    private lateinit var isLikeUser: String
    private var isLike: Int = 0

    //
    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_details)

        //
        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        //
        ivBack = findViewById(R.id.ivBack)

        //
        recyclerViewPostDetails = findViewById(R.id.recyclerViewPostDetails)
        recyclerViewPostDetails.visibility = View.GONE // check if dataset is ready to use, if not, then wait until post delayed

        //
        disposables = CompositeDisposable()

        //
        val position = intent.getStringExtra("position")
        if (position != null) {
            Handler().postDelayed(
                Runnable {
                    recyclerViewPostDetails.scrollToPosition(position.toInt())
                    recyclerViewPostDetails.visibility = View.VISIBLE
                }, 200
            )
        } else {
            recyclerViewPostDetails.visibility = View.VISIBLE
        }


        //
        ivBack.setOnClickListener {
            val i = Intent(this, BottomNavActivity::class.java)
            startActivity(i)
        }

        //
        initSearchRecyclerView()
        getExploreApiData(currentUser?.email!!)
        postLikeObservable()
        getLikeByUser(position.toString(), currentUser?.email!!)
    }

    private fun initSearchRecyclerView() {
        recyclerViewPostDetails = findViewById<RecyclerView>(R.id.recyclerViewPostDetails)
        recyclerViewPostDetails.apply {
            layoutManager = LinearLayoutManager(this@PostDetailsActivity)

            //
            postDetailsAdapter = PostDetailsAdapter(this@PostDetailsActivity)
            adapter = postDetailsAdapter
        }
    }

    private fun getExploreApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(PostDetailsActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(this, Observer {
            if (it != null) {
                postDetailsAdapter.setExploreList(it.data)
                postDetailsAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getExploreListOfData(input)
    }

    private fun getLikeByUser(id: String, email: String) {
        val viewModel = ViewModelProvider(this).get(PostDetailsActivityViewModel::class.java)
        viewModel.getLikeByUserObservable().observe(this, Observer {
            if (it != null) {
                postDetailsAdapter.setLikeList(it.data)
                postDetailsAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getLikeByUserOfData(id, email)
    }

    private fun postLike(like: Like) {
        val viewModel = ViewModelProvider(this).get(PostDetailsActivityViewModel::class.java)
        viewModel.postLikeToUserPostOfData(like)
    }

    private fun postLikeObservable() {
        val viewModel = ViewModelProvider(this).get(PostDetailsActivityViewModel::class.java)
        viewModel.postLikeToUserPostObservable().observe(this, Observer {
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

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    override fun updateLike(
        explore: Explore,
        holder: PostDetailsAdapter.MyViewHolder,
        position: Int
    ) {
        val currentUser = mAuth.currentUser

        if (isLike % 2 == 0) {
            holder.ivLike.setImageResource(R.drawable.ic_like)

            // post data
//            val like = Like(currentUser?.!!, explore.id, explore.email_user, "YES")
//            postLike(like)email

            //
            isLike++
        } else if (isLike % 2 == 1) {
            holder.ivLike.setImageResource(R.drawable.ic_unlike)

            // delete data


            //
            isLike++
        }

    }
}