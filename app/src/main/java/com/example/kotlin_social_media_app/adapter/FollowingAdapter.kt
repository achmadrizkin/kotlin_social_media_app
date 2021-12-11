package com.example.kotlin_social_media_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.model.user_following.UserFollowing

class FollowingAdapter: RecyclerView.Adapter<FollowingAdapter.MyViewHolder>() {
    private var userFollowing = ArrayList<UserFollowing>()

    fun setFollowingList(commentList: ArrayList<UserFollowing>) {
        this.userFollowing = commentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowingAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_user_following, parent, false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.MyViewHolder, position: Int) {
        holder.bind(userFollowing[position])
    }

    override fun getItemCount(): Int {
        return userFollowing.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvEmail = view.findViewById<TextView>(R.id.tvEmail)
        val thumbImageView = view.findViewById<ImageView>(R.id.thumbImageView)


        fun bind(data: UserFollowing) {
            tvTitle.text = data.name_user
            tvEmail.text = data.email_user

            Glide.with(thumbImageView).load(data.image_url).circleCrop().into(thumbImageView)
        }
    }
}