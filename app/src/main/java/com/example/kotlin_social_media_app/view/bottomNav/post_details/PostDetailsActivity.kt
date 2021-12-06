package com.example.kotlin_social_media_app.view.bottomNav.post_details

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.PostDetailsAdapter
import com.example.kotlin_social_media_app.view.bottomNav.BottomNavActivity
import com.example.kotlin_social_media_app.view.bottomNav.search.SearchFragment
import com.example.kotlin_social_media_app.view_model.PostDetailsActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class PostDetailsActivity : AppCompatActivity() {
    lateinit var postDetailsAdapter: PostDetailsAdapter
    lateinit var recyclerViewPostDetails: RecyclerView

    lateinit var ivBack: ImageView

    //
    private lateinit var mAuth: FirebaseAuth

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

        //
        disposables = CompositeDisposable()

        //
        ivBack.setOnClickListener {
            val i = Intent(this, BottomNavActivity::class.java)
            startActivity(i)
        }

        //
        initSearchRecyclerView()
        getExploreApiData(currentUser?.email!!)

    }

    private fun initSearchRecyclerView() {
        recyclerViewPostDetails = findViewById<RecyclerView>(R.id.recyclerViewPostDetails)
        recyclerViewPostDetails.apply {
            layoutManager = LinearLayoutManager(this@PostDetailsActivity)

            //
            postDetailsAdapter = PostDetailsAdapter()
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

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }
}