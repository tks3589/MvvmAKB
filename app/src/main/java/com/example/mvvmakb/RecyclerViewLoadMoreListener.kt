package com.example.mvvmakb

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreListener(layoutManager: LinearLayoutManager, visibleThreshold:Int): RecyclerView.OnScrollListener() {
    private val layoutManager = layoutManager
    private val visibleThreshold = visibleThreshold
    private lateinit var mOnLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var isScrolledToEnd = false
    private var totalItemCount: Int = 0
    private var lastVisibleItem: Int = 0

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener
    }

    fun setIsScrolledToEnd() {
        isScrolledToEnd = true
    }

    fun setLoaded() {
        isLoading = false
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

        totalItemCount = layoutManager.itemCount
        lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && !isScrolledToEnd && totalItemCount <= lastVisibleItem + visibleThreshold) {
            mOnLoadMoreListener.onLoadMore()
            isLoading = true
        }
    }
}