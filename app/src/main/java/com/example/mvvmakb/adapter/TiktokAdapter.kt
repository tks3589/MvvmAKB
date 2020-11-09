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
import com.example.mvvmakb.model.Tiktok
import kotlinx.android.synthetic.main.item_tiktok.view.*

class TiktokAdapter(context: Context,tiktokArrayList: ArrayList<Tiktok>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val mContext = context
    private val tiktokArrayList = tiktokArrayList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TiktokViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tiktok,parent,false))
    }

    override fun getItemCount(): Int {
        return tiktokArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TiktokViewHolder)
            holder.bind(tiktokArrayList[position])
    }

    inner class TiktokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tiktok_imgview_back = itemView.tiktok_imgview_back
        fun bind(tiktok: Tiktok){
            Glide.with(mContext)
                .load(tiktok.imgurl)
                .transform(CenterCrop(), RoundedCorners(10))
                .into(tiktok_imgview_back)
        }
    }
}