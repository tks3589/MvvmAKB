package com.example.mvvmakb.adapter

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.mvvmakb.R
import com.example.mvvmakb.fragment.MainImgsFragment
import com.example.mvvmakb.model.*
import com.example.mvvmakb.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_main_imgs.view.*

class MainAdapter()  : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_DONE = 1
    private val VIEW_TYPE_MAINIMGS_ITEM = 2
    private val VIEW_TYPE_STORIES_ITEM = 3
    private val VIEW_TYPE_TIKTOK_ITEM = 4
    private val VIEW_TYPE_VIDEO_ITEM = 5
    private val VIEW_TYPE_IG_ITEM = 6
    private lateinit var mViewModel: MainViewModel
    private lateinit var mContext: Context
    private lateinit var mainData: MainData
    private var mainImgsArrayList: ArrayList<MainImgs> = ArrayList()
    private var storiesArrayList: ArrayList<Stories> = ArrayList()
    private var tiktokArrayList: ArrayList<Tiktok>  = ArrayList()
    private var igvideoArrayList: ArrayList<Ig>  = ArrayList()
    private var igpostArrayList: ArrayList<Ig>  = ArrayList()

    constructor(context: Context):this(){
        mContext = context as FragmentActivity
        mViewModel = ViewModelProvider(context).get(MainViewModel::class.java)
        mViewModel.getLiveData().observe(context,androidx.lifecycle.Observer {
            mainData = it
            mainImgsArrayList = mainData.mainImgsArrayList
            storiesArrayList = mainData.storiesArrayList
            tiktokArrayList = mainData.tiktokArrayList
            igvideoArrayList = mainData.igVideoArrayList
            igpostArrayList = mainData.igPostArrayList
            notifyDataSetChanged()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_MAINIMGS_ITEM -> MainImgsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_imgs,parent,false))
            VIEW_TYPE_STORIES_ITEM -> StoriesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_stories,parent,false))
            VIEW_TYPE_TIKTOK_ITEM -> TiktokViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_tiktok,parent,false))
            VIEW_TYPE_VIDEO_ITEM -> VideoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_igvideo,parent,false))
            VIEW_TYPE_IG_ITEM -> IgViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_igpost,parent,false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false))
            else -> DoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_done,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        if(mainImgsArrayList.size>0)
            return VIEW_TYPE_MAINIMGS_ITEM
        else
            return VIEW_TYPE_LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is MainImgsViewHolder)
            holder.bind(mainImgsArrayList)
    }

    inner class MainImgsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val mainimgs_linear_h = itemView.mainimgs_linear_h
        private val mainimgs_viewpager = itemView.mainimgs_viewpager

        fun bind(mainImgsArrayList: ArrayList<MainImgs>){ //注意bind執行次數 getItemCount會從0開始
            var mainImgsFragmentList:ArrayList<MainImgsFragment> = ArrayList()
            if(mainImgsArrayList.size > 1) {
                for (i in 0 until mainImgsArrayList.size) {
                    val view = View(mContext)
                    view.setBackgroundResource(R.drawable.mainimgs_dot)
                    view.isEnabled = false
                    val dot_dis = mContext.resources.getDimension(R.dimen.dot_dis).toInt()
                    val layoutParams = LinearLayout.LayoutParams(dot_dis, dot_dis)
                    layoutParams.leftMargin = 10
                    mainimgs_linear_h.addView(view, layoutParams)
                    mainImgsFragmentList.add(getMainImgsFragment(mainImgsArrayList[i]))
                }
                mainimgs_linear_h.getChildAt(0).isEnabled = true
                mainimgs_viewpager.offscreenPageLimit = 9
                mainimgs_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                    var tmp = 0
                    override fun onPageScrollStateChanged(state: Int) {
                    }
                    override fun onPageScrolled(position: Int, positionOffset: Float,positionOffsetPixels: Int) {
                    }
                    override fun onPageSelected(position: Int) {
                        mainimgs_linear_h.getChildAt(tmp).isEnabled = false
                        mainimgs_linear_h.getChildAt(position).isEnabled = true
                        tmp = position
                    }
                })
            }else{
                mainImgsFragmentList.add(getMainImgsFragment(mainImgsArrayList[0]))
            }

            val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
            mainimgs_viewpager.adapter = MainImgsPagerAdapter(fragmentManager,mainImgsFragmentList)
        }
    }

    fun getMainImgsFragment(mainImgs:MainImgs) : MainImgsFragment{
        val mainImgsFragment = MainImgsFragment.newInstance()
        val bundle = Bundle()
        bundle.putString("imgurl",mainImgs.imgurl)
        bundle.putString("dataurl",mainImgs.dataurl)
        mainImgsFragment.arguments = bundle

        return mainImgsFragment
    }

    inner class StoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){

        }
    }
    inner class TiktokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){

        }
    }
    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){

        }
    }
    inner class IgViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(){

        }
    }
    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}