package com.example.kotlin_social_media_app.adapter

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin_social_media_app.R
import com.example.kotlin_social_media_app.model.ReelsModel

class ReelsAdapter(arrVideo: ArrayList<ReelsModel>) :
    RecyclerView.Adapter<ReelsAdapter.VideoViewHolder>() {
    var arrVideoModel: ArrayList<ReelsModel> = arrVideo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return arrVideoModel.size
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.setVideoData(arrVideoModel[position])
    }

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        val tvDesc = view.findViewById<TextView>(R.id.tvDesc)
        val videoView = view.findViewById<VideoView>(R.id.videoView)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)


        fun setVideoData(videoModel: ReelsModel) {
            tvTitle.text = videoModel.videoTitle
            tvDesc.text = videoModel.videoDesc
            videoView.setVideoPath(videoModel.videoUrl)

            //
            videoView.setOnPreparedListener(object : MediaPlayer.OnPreparedListener {
                override fun onPrepared(mp: MediaPlayer) {
                    progressBar.visibility = View.GONE
                    mp.start()
                    val videoRatio = mp.videoWidth.toFloat() / mp.videoHeight.toFloat()
                    val screenRatio =
                        videoView.width.toFloat() / videoView.height.toFloat()

                    val scale = videoRatio / screenRatio
                    if (scale > 1f) {
                        videoView.scaleX = scale
                    } else {
                        videoView.scaleY = (1f / scale)
                    }
                }

            })

            videoView.setOnCompletionListener {
                object : MediaPlayer.OnCompletionListener {
                    override fun onCompletion(mp: MediaPlayer) {
                        mp.start()
                    }
                }
            }

        }

    }
}