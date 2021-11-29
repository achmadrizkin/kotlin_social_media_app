package com.example.kotlin_social_media_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.User

class SearchUserAdapter: RecyclerView.Adapter<SearchUserAdapter.MyViewHolder>() {
    private var userList = ArrayList<User>()

    fun setBookList(userList: ArrayList<User>) {
        this.userList = userList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchUserAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_row, parent, false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: SearchUserAdapter.MyViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvPublisher = view.findViewById<TextView>(R.id.tvPublisher)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val thumbImageView = view.findViewById<ImageView>(R.id.thumbImageView)

        fun bind(data: User) {
            tvTitle.text = data.name_user
            tvPublisher.text = "Rp. " + data.email_user
            tvDescription.text = data.following.toString()

            val url = data.image_url
            Glide.with(thumbImageView).load(url).circleCrop().into(thumbImageView)
        }
    }
}