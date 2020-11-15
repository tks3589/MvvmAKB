package com.example.mvvmakb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.R
import com.example.mvvmakb.model.News
import com.example.mvvmakb.viewmodel.NewsViewModel
import com.example.mvvmakb.viewmodel.UpcomingViewModel
import kotlinx.android.synthetic.main.item_notification.view.*

class EventsAdapter() : BaseAdapter() {
    private lateinit var mContext: FragmentActivity
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var upcomingViewModel: UpcomingViewModel
    private lateinit var mListener: OnReceiveDataListener
    private var newsArrayList: ArrayList<News> = ArrayList()
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_DONE = 2
    private var LAST_ONE = false
    private var TYPE_TAG = "news"

    constructor(content:Context,type:String) : this(){
        mContext = content as FragmentActivity
        if(type == "news") {
            newsViewModel = ViewModelProvider(mContext).get(NewsViewModel::class.java)
            newsViewModel.setListener(object :OnReceiveDataListener{
                override fun onReceiveData() {
                    mListener.onReceiveData()
                    if(!newsViewModel.isInit()){
                        newsViewModel.setInit(true)
                    }
                }

                override fun onFailedToReceiveData() {
                    mListener.onFailedToReceiveData()
                }

                override fun onNoMoreData() {
                    mListener.onNoMoreData()
                    LAST_ONE = true
                }

            })
            newsViewModel.getLiveData().observe(mContext, androidx.lifecycle.Observer {
                newsArrayList = it
                notifyDataSetChanged()
            })
        }else{
            TYPE_TAG = "upcoming"
            upcomingViewModel = ViewModelProvider(mContext).get(UpcomingViewModel::class.java)
            upcomingViewModel.setListener(object :OnReceiveDataListener{
                override fun onReceiveData() {
                    mListener.onReceiveData()
                    if(!upcomingViewModel.isInit()){
                        upcomingViewModel.setInit(true)
                    }
                }

                override fun onFailedToReceiveData() {
                    mListener.onFailedToReceiveData()
                }

                override fun onNoMoreData() {
                    mListener.onNoMoreData()
                    LAST_ONE = true
                }

            })
            upcomingViewModel.getLiveData().observe(mContext, androidx.lifecycle.Observer {
                newsArrayList = it
                notifyDataSetChanged()
            })
        }
        refresh()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = View.inflate(mContext,R.layout.item_loading, null)
        if (getItemViewType(position) == VIEW_TYPE_ITEM){
            view = View.inflate(mContext,R.layout.item_notification, null)
            view.notification_content.text = newsArrayList[position].title
            view.notification_date.text = newsArrayList[position].date
        }else if(getItemViewType(position) == VIEW_TYPE_DONE){
            view = View.inflate(mContext,R.layout.item_done, null)
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
        return when {
            position < newsArrayList.size -> VIEW_TYPE_ITEM
            LAST_ONE -> VIEW_TYPE_DONE
            else -> VIEW_TYPE_LOADING
        }
    }

    override fun isEnabled(position: Int): Boolean {
        return getItemViewType(position) != VIEW_TYPE_LOADING
    }

    fun refresh(){
        LAST_ONE = false
        if(TYPE_TAG == "news") {
            newsViewModel.refreshData()
            newsViewModel.getPage().let {
                newsViewModel.loadNewsData(it)
            }
        }
        else {
            upcomingViewModel.refreshData()
            upcomingViewModel.getPage().let {
                upcomingViewModel.loadUpcomingData(it)
            }
        }
    }

    fun isInit():Boolean{
        return if(TYPE_TAG == "news"){
            newsViewModel.isInit()
        }else{
            upcomingViewModel.isInit()
        }
    }

    fun loadData(){
        if(TYPE_TAG == "news"){
            newsViewModel.addPage()
            newsViewModel.getPage().let {
                newsViewModel.loadNewsData(it)
            }
        }else{
            upcomingViewModel.addPage()
            upcomingViewModel.getPage().let {
                upcomingViewModel.loadUpcomingData(it)
            }
        }
    }

    fun setListener(onReceiveDataListener: OnReceiveDataListener){
        mListener = onReceiveDataListener
    }

}