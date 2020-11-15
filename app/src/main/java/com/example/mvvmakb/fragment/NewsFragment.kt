package com.example.mvvmakb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.mvvmakb.ListViewLoadMoreListener
import com.example.mvvmakb.OnLoadMoreListener
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.EventsAdapter
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : Fragment() {
    private lateinit var eventsAdapter: EventsAdapter

    companion object{
        val instance : NewsFragment by lazy {
            NewsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news,container,false)
        eventsAdapter = EventsAdapter(view.context,"news")
        val scrollListener = ListViewLoadMoreListener(1).apply {
            setOnLoadMoreListener(object :OnLoadMoreListener{
                override fun onLoadMore() {
                    if(eventsAdapter.isInit())
                        eventsAdapter.loadData()
                }

            })
        }
        eventsAdapter.setListener(object : OnReceiveDataListener{
            override fun onReceiveData() {
                scrollListener.setLoaded()
            }

            override fun onFailedToReceiveData() {
                Toast.makeText(view.context,"壞了", Toast.LENGTH_SHORT).show()
            }

            override fun onNoMoreData() {
                scrollListener.setIsScrolledToEnd()
            }

        })
        view.news_listView.adapter = eventsAdapter
        view.news_listView.setOnScrollListener(scrollListener)
        view.swipeRefreshLayout.apply {
            setOnRefreshListener {
                eventsAdapter.refresh()
                scrollListener.resetScroll()
                isRefreshing = false
            }
        }

        return view
    }
}