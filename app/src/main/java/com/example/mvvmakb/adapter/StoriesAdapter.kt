package com.example.mvvmakb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mvvmakb.R
import com.example.mvvmakb.model.Stories
import kotlinx.android.synthetic.main.item_stories.view.*

class StoriesAdapter(context:Context,storiesArrayList: ArrayList<Stories>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val mContext = context
    private val storiesArrayList = storiesArrayList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return StoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_stories,parent,false))
    }

    override fun getItemCount(): Int {
        return storiesArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is StoriesViewHolder)
            holder.bind(storiesArrayList[position])
    }

    inner class StoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val stories_imgview_back = itemView.stories_imgview_back
        private val stories_imgview_circle= itemView.stories_imgview_circle
        fun bind(stories: Stories){
            Glide.with(mContext)
                .load(getImageUrl(stories.url))
                .transform(CenterCrop(),RoundedCorners(20))
                .into(stories_imgview_back)

            Glide.with(mContext)
                .load(stories.circle_url)
                .circleCrop()
                .into(stories_imgview_circle)
        }

        private fun getImageUrl(url:String):String{
            val url = url.trim()
            val data = url.split(",")
            return if(data.size == 2 && data[0].indexOf("jpg")!=-1){
                data[0].trim()
            }else{
                url
            }
        }
    }
}