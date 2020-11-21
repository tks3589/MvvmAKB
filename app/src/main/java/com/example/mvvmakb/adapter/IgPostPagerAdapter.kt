package com.example.mvvmakb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmakb.R
import kotlinx.android.synthetic.main.item_igpost_img.view.*
import kotlinx.android.synthetic.main.item_igpost_video.view.*

class IgPostPagerAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private lateinit var mContext :Context
    private var video_preview_urls = ArrayList<String>()
    private var data_urls = ArrayList<String>()
    private val VIEW_TYPE_VIDEO = 0
    private val VIEW_TYPE_IMG = 1


    constructor(context: Context,data:ArrayList<String>):this(){
        this.mContext = context
        processData(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_VIDEO -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_igpost_video,parent,false))
            else -> ImgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_igpost_img,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return data_urls.size
    }

    override fun getItemViewType(position: Int): Int {
        return if(data_urls[position].indexOf(".mp4") != -1)
            VIEW_TYPE_VIDEO
        else
            VIEW_TYPE_IMG
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is VideoViewHolder -> holder.bind(data_urls[position])
            is ImgViewHolder -> holder.bind(data_urls[position])
        }
    }

    private fun processData(data: ArrayList<String>){
        for(url in data){
            var p_url = url.replace('\'',' ').trim()
            if(p_url.indexOf("video")!=-1 && p_url.indexOf("mp4")==-1){
                video_preview_urls.add(p_url)
            }else
                data_urls.add(p_url)
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var preview_url = ""
        private var video_path = ""
        private var imageview = itemView.igpost_video_imageview
        fun bind(data_url:String){
            processData(data_url)
            Glide.with(mContext).load(preview_url).into(imageview)
        }

        private fun processData(data_url: String){
            var videoID = data_url.split(":")[0]
            video_path = data_url.substring(data_url.indexOf(":")+1)
            for(url in video_preview_urls){
                if(url.indexOf(videoID)!=-1 && url.indexOf(".jpg")!=-1){
                    preview_url = url.substring(url.indexOf(":")+1)
                    break
                }
            }
        }
    }

    inner class ImgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var imageview = itemView.igpost_img_imageview
        fun bind(img_path:String){
            Glide.with(mContext).load(img_path).into(imageview)
        }
    }


}