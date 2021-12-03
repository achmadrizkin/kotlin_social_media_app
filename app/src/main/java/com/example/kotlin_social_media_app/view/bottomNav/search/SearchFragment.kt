package com.example.kotlin_social_media_app.view.bottomNav.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.*
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ExploreAdapter
import com.example.kotlin_social_media_app.adapter.SearchUserAdapter
import com.example.kotlin_social_media_app.view_model.SearchActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var searchUserAdapter: SearchUserAdapter
    lateinit var exploreAdapter: ExploreAdapter

    var disposables: CompositeDisposable? = null

    lateinit var recyclerViewSearch : RecyclerView
    lateinit var recyclerViewLayout : RecyclerView
    lateinit var inputBookName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        disposables = CompositeDisposable()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //
        recyclerViewSearch = view.findViewById(R.id.searchView)
        recyclerViewLayout = view.findViewById(R.id.rcyclerViewLayout)
        inputBookName = view.findViewById(R.id.inputBookName)

        loadApiData(inputBookName.text.toString())


        //
        initExploreRecyclerView(view)
        initSearchRecyclerView(view)

        initSearchBook()
        getExploreApiData("a")


        return view
    }

    private fun initSearchRecyclerView(view: View) {
        recyclerViewSearch = view.findViewById<RecyclerView>(R.id.searchView)
        recyclerViewSearch.apply {
            layoutManager = LinearLayoutManager(activity)

            //
            searchUserAdapter = SearchUserAdapter()
            adapter = searchUserAdapter
        }
    }

    private fun initExploreRecyclerView(view: View) {
        recyclerViewLayout.apply {
            layoutManager = GridLayoutManager(activity, 3)

            exploreAdapter = ExploreAdapter()
            adapter = exploreAdapter
        }
    }

    private fun initSearchBook() {
        inputBookName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
                loadApiData(s.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    fun getExploreApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.getExploreObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                exploreAdapter.setExploreList(it.data)
                exploreAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getExploreListOfData(input)
    }

    fun loadApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.getUserByNameObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerViewSearch.visibility = View.VISIBLE
                recyclerViewLayout.visibility = View.GONE

                //
                searchUserAdapter.setBookList(it.data)
                searchUserAdapter.notifyDataSetChanged()
            } else {
                recyclerViewSearch.visibility = View.GONE
                recyclerViewLayout.visibility = View.VISIBLE
            }

            if (input.isEmpty() || input == "") {
                recyclerViewSearch.visibility = View.GONE
                recyclerViewLayout.visibility = View.VISIBLE
            }
        })
        viewModel.searchUserListOfData(input)
    }

    override fun onDestroy() {
        disposables!!.clear()
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}