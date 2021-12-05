package com.example.kotlin_social_media_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.explore.Explore
import com.example.kotlin_social_media_app.model.product.Product

class ShopAdapter: RecyclerView.Adapter<ShopAdapter.MyViewHolder>() {
    private var shopList = ArrayList<Product>()

    fun setShopList(shopList: ArrayList<Product>) {
        this.shopList = shopList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShopAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.recycler_list_shop, parent, false)

        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ShopAdapter.MyViewHolder, position: Int) {
        holder.bind(shopList[position])
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val thumbImageViewExplore = view.findViewById<ImageView>(R.id.thumbImageViewExplore)

        fun bind(data: Product) {
            val url = data.image_url
            Glide.with(thumbImageViewExplore).load(url).into(thumbImageViewExplore)
        }
    }
}