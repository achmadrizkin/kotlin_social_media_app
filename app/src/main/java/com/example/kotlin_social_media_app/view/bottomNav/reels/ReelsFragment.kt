package com.example.kotlin_social_media_app.view.bottomNav.reels

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.adapter.ReelsAdapter
import com.example.kotlin_social_media_app.model.ReelsModel

class ReelsFragment : Fragment() {
    var arrVideoModel = ArrayList<ReelsModel>()
    var videoAdapter: ReelsAdapter? = null

    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reels, container, false)

        //
        viewPager = view.findViewById(R.id.viewPager)

        arrVideoModel.add(ReelsModel("Tree with flowers","The branches of a tree wave in the breeze, with pointy leaves ","https://assets.mixkit.co/videos/preview/mixkit-tree-with-yellow-flowers-1173-large.mp4"))
        arrVideoModel.add(ReelsModel("multicolored lights","A man with a small beard and mustache wearing a white sweater, sunglasses, and a backwards black baseball cap turns his head in different directions under changing colored lights.","https://assets.mixkit.co/videos/preview/mixkit-man-under-multicolored-lights-1237-large.mp4"))
        arrVideoModel.add(ReelsModel("holding neon light","Bald man with a short beard wearing a large jean jacket holds a long tubular neon light thatch","https://assets.mixkit.co/videos/preview/mixkit-man-holding-neon-light-1238-large.mp4"))
        arrVideoModel.add(ReelsModel("Sun over hills","The sun sets or rises over hills, a body of water beneath them.","https://assets.mixkit.co/videos/preview/mixkit-sun-over-hills-1183-large.mp4"))

        videoAdapter = ReelsAdapter(arrVideoModel)
        viewPager.adapter = videoAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = ReelsFragment()
    }
}