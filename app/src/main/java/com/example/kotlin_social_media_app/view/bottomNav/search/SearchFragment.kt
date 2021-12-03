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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.SearchUserAdapter
import com.example.kotlin_social_media_app.view_model.SearchActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    lateinit var searchUserAdapter: SearchUserAdapter

    lateinit var recyclerView : RecyclerView
    lateinit var inputBookName : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        //
        recyclerView = view.findViewById(R.id.searchView)
        inputBookName = view.findViewById(R.id.inputBookName)

        loadApiData(inputBookName.text.toString())


        //
        initSearchBook()
        initRecyclerView(view)

        return view
    }

    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById<RecyclerView>(R.id.searchView)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            val decoration = DividerItemDecoration(activity, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(decoration)

            //
            searchUserAdapter = SearchUserAdapter()
            adapter = searchUserAdapter
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

    fun loadApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.getUserByNameObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                recyclerView.visibility = View.VISIBLE
                searchUserAdapter.setBookList(it.data)
                searchUserAdapter.notifyDataSetChanged()
            } else {
                recyclerView.visibility = View.GONE
            }

            if (input.isEmpty() || input == "") {
                recyclerView.visibility = View.GONE
            }
        })
        viewModel.searchUserListOfData(input)
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}