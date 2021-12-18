package com.example.kotlin_social_media_app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.comment.Comment
import com.example.kotlin_social_media_app.view.bottomNav.user_details_explore.UserDetailsExploreActivity

class CommentAdapter: RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    private var commentList = ArrayList<Comment>()

    fun setCommentList(commentList: ArrayList<Comment>) {
        this.commentList = commentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_comment, parent, false)

        return CommentAdapter.MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: CommentAdapter.MyViewHolder, position: Int) {
        holder.bind(commentList[position])
        holder.ivPicture.setOnClickListener {
            val i = Intent(it.context, UserDetailsExploreActivity::class.java)

            i.putExtra("email1", commentList[position].email_user)

            it.context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val ivPicture = view.findViewById<ImageView>(R.id.ivPicture)
        val tvNameUser = view.findViewById<TextView>(R.id.tvNameUser)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)

        fun bind(data: Comment) {
            tvNameUser.text = data.name_user
            tvDescription.text = data.comment

            Glide.with(ivPicture).load(data.image_url).circleCrop().into(ivPicture)
        }
    }
}