package com.example.kotlin_social_media_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.explore.Explore

class PostDetailsAdapter: RecyclerView.Adapter<PostDetailsAdapter.MyViewHolder>() {
    private var exploreList = ArrayList<Explore>()

    fun setExploreList(exploreList: ArrayList<Explore>) {
        this.exploreList = exploreList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostDetailsAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_post_details, parent, false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: PostDetailsAdapter.MyViewHolder, position: Int) {
        holder.bind(exploreList[position])
    }

    override fun getItemCount(): Int {
        return exploreList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvInstagramName = view.findViewById<TextView>(R.id.tvInstagramName)
        val tvLikePost = view.findViewById<TextView>(R.id.tvLikePost)
        val tvDescriptionPost = view.findViewById<TextView>(R.id.tvDescriptionPost)


        val ivProfilePicture = view.findViewById<ImageView>(R.id.ivProfilePicture)
        val ivPicturePost = view.findViewById<ImageView>(R.id.ivPicturePost)

        fun bind(data: Explore) {
            tvInstagramName.text = data.name_user
            tvLikePost.text = data.like_post + " Likes"
            tvDescriptionPost.text = data.description_post

            Glide.with(ivProfilePicture).load(data.image_url).circleCrop().into(ivProfilePicture)
            Glide.with(ivPicturePost).load(data.image_post).into(ivPicturePost)
        }
    }

}