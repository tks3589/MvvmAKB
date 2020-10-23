package com.example.mvvmakb.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mvvmakb.R
import com.example.mvvmakb.model.Member
import kotlinx.android.synthetic.main.item_gridview_member.view.*

class MemberGridViewAdapter(context: Context,itemGridList:ArrayList<Member>) :BaseAdapter() {
    private val context = context
    private val itemGridList = itemGridList

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView?:View.inflate(context, R.layout.item_gridview_member,null)
        view.mb_name.text = itemGridList[position].cname
        val imageview = view.mb_imageview
        imageview.scaleType = ImageView.ScaleType.FIT_CENTER
        Glide.with(context)
            .load(itemGridList[position].imgurl)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(200,200)
            .into(imageview)

        return view
    }

    override fun getItem(position: Int): Any {
        return itemGridList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return itemGridList.size
    }
}