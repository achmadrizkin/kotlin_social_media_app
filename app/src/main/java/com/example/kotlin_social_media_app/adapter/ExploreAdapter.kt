package com.example.kotlin_social_media_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.explore.Explore

class ExploreAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<ExploreAdapter.MyViewHolder>() {
    private var exploreList = ArrayList<Explore>()
    private val lastCheckedPosition = -1

    fun setExploreList(exploreList: ArrayList<Explore>) {
        this.exploreList = exploreList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExploreAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_explore, parent, false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ExploreAdapter.MyViewHolder, position: Int) {
        holder.bind(exploreList[position])
        holder.itemView.setOnClickListener {
            clickListener.onItemClickListenerExplore(exploreList[position], position)
        }
    }

    override fun getItemCount(): Int {
        return exploreList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImageViewExplore = view.findViewById<ImageView>(R.id.thumbImageViewExplore)

        fun bind(data: Explore) {
            val url = data.image_post
            Glide.with(thumbImageViewExplore).load(url).into(thumbImageViewExplore)
        }
    }

    interface OnItemClickListener {
        fun onItemClickListenerExplore(explore: Explore, position: Int)
    }
}