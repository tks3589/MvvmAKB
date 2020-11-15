package com.example.mvvmakb.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmakb.OnLoadMoreListener
import com.example.mvvmakb.R
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.RecyclerViewLoadMoreListener
import com.example.mvvmakb.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(){

    companion object{
        val instance : MainFragment by lazy {
            MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val adapter = MainAdapter(view.context)
        val layoutManager = LinearLayoutManager(view.context)
        val scrollListener = RecyclerViewLoadMoreListener(layoutManager,10).apply {
            setOnLoadMoreListener(object :OnLoadMoreListener{
                override fun onLoadMore() {
                    if(adapter.isInit())
                        adapter.loadIgPost()
                }
            })
        }
        adapter.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {
                scrollListener.setLoaded()
            }

            override fun onFailedToReceiveData() {
                Toast.makeText(view.context,"壞了",Toast.LENGTH_SHORT).show()
            }

            override fun onNoMoreData() {
                scrollListener.setIsScrolledToEnd()
            }

        })
        view.recyclerView.layoutManager = layoutManager
        view.recyclerView.adapter =  adapter
        view.recyclerView.addOnScrollListener(scrollListener)
        view.swipeRefreshLayout.apply {
            setOnRefreshListener {
                adapter.refresh()
                scrollListener.resetScroll()
                isRefreshing = false
            }
        }
        return view
    }
}