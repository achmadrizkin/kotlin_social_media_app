package com.example.kotlin_social_media_app.view.bottomNav.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.view_model.SearchActivityViewModel
import com.example.kotlin_social_media_app.view_model.UserActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    lateinit var recyclerViewUserLayout : RecyclerView
    lateinit var exploreAdapter: ExploreAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        recyclerViewUserLayout = view.findViewById(R.id.searchViewUser)

        initExploreRecyclerView(view)
        getExploreByEmailApiData("achmadrizki22@gmail.com")

        return view
    }

    private fun initExploreRecyclerView(view: View) {
        recyclerViewUserLayout = view.findViewById<RecyclerView>(R.id.searchViewUser)
        recyclerViewUserLayout.apply {
            layoutManager = GridLayoutManager(activity, 3)

            exploreAdapter = ExploreAdapter()
            adapter = exploreAdapter
        }
    }

    fun getExploreByEmailApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(UserActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                exploreAdapter.setExploreList(it.data)
                exploreAdapter.notifyDataSetChanged()
            } else {
                recyclerViewUserLayout.visibility = View.GONE
            }
        })
        viewModel.getExploreListOfData(input)
    }

    companion object {
        @JvmStatic
        fun newInstance() = UserFragment()
    }
}