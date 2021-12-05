package com.example.kotlin_social_media_app.view.bottomNav.reels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ReelsAdapter
import com.example.kotlin_social_media_app.view_model.ReelsActivityViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class ReelsFragment : Fragment() {
    lateinit var reelsAdapter: ReelsAdapter
    lateinit var viewPager: ViewPager2

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
        val view = inflater.inflate(R.layout.fragment_reels, container, false)

        //
        viewPager = view.findViewById(R.id.viewPager)

        //
        initGetReelsViewPager(view)
        getReelsListApiData("a")

        return view
    }

    override fun onDestroy() {
        disposables!!.dispose()
        super.onDestroy()
    }

    private fun initGetReelsViewPager(view: View) {
        viewPager = view.findViewById(R.id.viewPager)

        reelsAdapter = ReelsAdapter()
        viewPager.adapter = reelsAdapter
    }

    fun getReelsListApiData(input: String) {
        val viewModel = ViewModelProvider(this).get(ReelsActivityViewModel::class.java)
        viewModel.getReelsByNotEmailAndOrderByLikeObservable().observe(viewLifecycleOwner, Observer {
            if (it != null) {
                reelsAdapter.setReelsList(it.data)
                reelsAdapter.notifyDataSetChanged()
            }
        })
        viewModel.getReelsByNotEmailAndOrderByLikeOfData(input)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReelsFragment()
    }
}