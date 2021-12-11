package com.example.kotlin_social_media_app.view.bottomNav.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.PostDetailsAdapter
import com.example.kotlin_social_media_app.view_model.HomeActivityViewModel
import com.example.kotlin_social_media_app.view_model.PostDetailsActivityViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var postDetailsAdapter: PostDetailsAdapter
    lateinit var recyclerViewHome: RecyclerView
    private lateinit var mAuth: FirebaseAuth

    private lateinit var ivFollowPeople: ImageView
    private lateinit var tvFollowPeople: TextView


    var disposables: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser


        recyclerViewHome = view.findViewById(R.id.recyclerViewHome)
        ivFollowPeople = view.findViewById(R.id.ivFollowPeople)
        tvFollowPeople = view.findViewById(R.id.tvFollowPeople)

        getExploreApiData(currentUser?.email!!)
        initSearchRecyclerView(view)

        return view
    }

    private fun getExploreApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(HomeActivityViewModel::class.java)
        viewModel.getFollowingByUserObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                postDetailsAdapter.setExploreList(it.data)
                postDetailsAdapter.notifyDataSetChanged()

                //
                tvFollowPeople.visibility = View.GONE
                ivFollowPeople.visibility = View.GONE
            } else {
                tvFollowPeople.visibility = View.VISIBLE
                ivFollowPeople.visibility = View.VISIBLE
            }
        })
        viewModel.getFollowingByUserOfData(input)
    }

    private fun initSearchRecyclerView(view: View) {
        recyclerViewHome = view.findViewById<RecyclerView>(R.id.recyclerViewHome)
        recyclerViewHome.apply {
            layoutManager = LinearLayoutManager(activity)

            //
            postDetailsAdapter = PostDetailsAdapter()
            adapter = postDetailsAdapter
        }
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}