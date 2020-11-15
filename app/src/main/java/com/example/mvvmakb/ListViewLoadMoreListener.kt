package com.example.mvvmakb

import android.util.Log
import android.widget.AbsListView

class ListViewLoadMoreListener(visibleThreshold :Int) : AbsListView.OnScrollListener{
    private val visibleThreshold = visibleThreshold
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var isScrolledToEnd = false

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setIsScrolledToEnd() {
        isScrolledToEnd = true
    }

    fun resetScroll(){
        isScrolledToEnd = false
    }

    fun setLoaded() {
        isLoading = false
    }

    override fun onScroll(
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
       // Log.d("onScroll","totalItemCount: $totalItemCount , firstVisibleItem: $firstVisibleItem , visibleItemCount: $visibleItemCount")

        if(totalItemCount==1) return

        if (!isLoading && !isScrolledToEnd
            && totalItemCount  <= firstVisibleItem + visibleItemCount + visibleThreshold) {
            mOnLoadMoreListener.onLoadMore()
            isLoading = true
        }
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
       // Log.d("onScollStateChanged","onScollStateChanged: $scrollState")
    }


}