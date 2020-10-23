package com.example.mvvmakb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmakb.R
import com.example.mvvmakb.model.News
import com.example.mvvmakb.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.item_notification.view.*

class NewsAdapter() : BaseAdapter() {
    private lateinit var mContext: FragmentActivity
    private lateinit var newsViewModel: NewsViewModel
    private var newsArrayList: ArrayList<News> = ArrayList()
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_ITEM = 1

    constructor(content:Context) : this(){
        mContext = content as FragmentActivity
        newsViewModel = ViewModelProvider(mContext).get(NewsViewModel::class.java)
        newsViewModel.getLiveData().observe(mContext,androidx.lifecycle.Observer {
            newsArrayList = it
            notifyDataSetChanged()
        })
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = View.inflate(mContext,R.layout.item_loading, null)
        if (getItemViewType(position) == VIEW_TYPE_ITEM){
            view = View.inflate(mContext,R.layout.item_notification, null)
            view.notification_content.text = newsArrayList[position].title
            view.notification_date.text = newsArrayList[position].date
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return if(position < newsArrayList.size)
            newsArrayList[position]
        else "loading..."
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return newsArrayList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position < newsArrayList.size)
            VIEW_TYPE_ITEM
        else
            VIEW_TYPE_LOADING
    }

}