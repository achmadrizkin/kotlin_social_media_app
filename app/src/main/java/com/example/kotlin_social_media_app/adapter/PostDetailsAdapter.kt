package com.example.kotlin_social_media_app.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.like.Like
import com.example.kotlin_social_media_app.model.like.LikeList
import com.example.kotlin_social_media_app.view.bottomNav.comment.CommentActivity
import com.example.kotlin_social_media_app.view.bottomNav.user_details_explore.UserDetailsExploreActivity
import com.midtrans.sdk.uikit.activities.UserDetailsActivity

class PostDetailsAdapter(val clickListener: OnItemClickListener): RecyclerView.Adapter<PostDetailsAdapter.MyViewHolder>() {
    private var exploreList = ArrayList<Explore>()
    private var likeList = ArrayList<Like>()

    fun setExploreList(exploreList: ArrayList<Explore>) {
        this.exploreList = exploreList
    }

    fun setLikeList(likeList: ArrayList<Like>) {
            this.likeList = likeList
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
        holder.ivComment.setOnClickListener {
            clickListener.getComment(exploreList[position], holder, position)
        }

        holder.ivProfilePicture.setOnClickListener {
            val i = Intent(it.context, UserDetailsExploreActivity::class.java)

            i.putExtra("email1", exploreList[position].email_user)

            it.context.startActivity(i)
        }

        holder.ivLike.setOnClickListener {
            clickListener.updateLike(exploreList[position], holder, position)
        }

        //
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
        val ivComment = view.findViewById<ImageView>(R.id.ivComment)
        val ivLike = view.findViewById<ImageView>(R.id.ivLike)

        fun bind(data: Explore) {
            tvInstagramName.text = data.name_user
            tvLikePost.text = data.like_post .toString() + " Likes"
            tvDescriptionPost.text = data.description_post

            Glide.with(ivProfilePicture).load(data.image_url).circleCrop().into(ivProfilePicture)
            Glide.with(ivPicturePost).load(data.image_post).into(ivPicturePost)
        }
    }

    interface OnItemClickListener {
        fun updateLike(explore: Explore, holder: MyViewHolder, position: Int)
        fun getComment(explore: Explore, holder: MyViewHolder, position: Int)
    }
}