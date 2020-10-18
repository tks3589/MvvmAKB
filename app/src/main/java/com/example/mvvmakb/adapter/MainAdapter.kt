package com.example.mvvmakb.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.mvvmakb.R
import com.example.mvvmakb.fragment.IgVideoFragment
import com.example.mvvmakb.fragment.MainImgsFragment
import com.example.mvvmakb.model.*
import com.example.mvvmakb.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.item_main_igpost.view.*
import kotlinx.android.synthetic.main.item_main_igvideo.view.*
import kotlinx.android.synthetic.main.item_main_imgs.view.*
import kotlinx.android.synthetic.main.item_main_stories.view.*
import kotlinx.android.synthetic.main.item_main_tiktok.view.*

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
            VIEW_TYPE_IG_ITEM -> IgPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_main_igpost,parent,false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false))
            else -> DoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_done,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return igpostArrayList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(mainImgsArrayList.size > 0 && position == 0)
            VIEW_TYPE_MAINIMGS_ITEM
        else if(storiesArrayList.size > 0 && position == 1)
            VIEW_TYPE_STORIES_ITEM
        else if(tiktokArrayList.size > 0 && position == 2)
            VIEW_TYPE_TIKTOK_ITEM
        else if(igvideoArrayList.size > 0 && position == 3)
            VIEW_TYPE_VIDEO_ITEM
        else if(igpostArrayList.size > 0 && position >= 4)
            VIEW_TYPE_IG_ITEM
        else
            VIEW_TYPE_LOADING
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MainImgsViewHolder -> holder.bind(mainImgsArrayList)
            is StoriesViewHolder -> holder.bind(storiesArrayList)
            is TiktokViewHolder -> holder.bind(tiktokArrayList)
            is VideoViewHolder -> holder.bind(igvideoArrayList)
            is IgPostViewHolder -> holder.bind(igpostArrayList[position-4])
        }
    }

    inner class MainImgsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val mainimgs_linear_h = itemView.mainimgs_linear_h
        private val mainimgs_viewpager = itemView.mainimgs_viewpager
        private var tag = itemView.tag
        fun bind(mainImgsArrayList: ArrayList<MainImgs>){ //注意bind執行次數 getItemCount會從0開始
            if(tag == null) {
                tag = true
                var mainImgsFragmentList: ArrayList<MainImgsFragment> = ArrayList()
                if (mainImgsArrayList.size > 1) {
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
                    mainimgs_viewpager.addOnPageChangeListener(object :
                        ViewPager.OnPageChangeListener {
                        var tmp = 0
                        override fun onPageScrollStateChanged(state: Int) {
                        }

                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                        }

                        override fun onPageSelected(position: Int) {
                            mainimgs_linear_h.getChildAt(tmp).isEnabled = false
                            mainimgs_linear_h.getChildAt(position).isEnabled = true
                            tmp = position
                        }
                    })
                } else {
                    mainImgsFragmentList.add(getMainImgsFragment(mainImgsArrayList[0]))
                }

                val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
                mainimgs_viewpager.adapter = MainImgsPagerAdapter(fragmentManager, mainImgsFragmentList)
            }
        }

        private fun getMainImgsFragment(mainImgs:MainImgs) : MainImgsFragment{
            val mainImgsFragment = MainImgsFragment.newInstance()
            val bundle = Bundle()
            bundle.putString("imgurl",mainImgs.imgurl)
            bundle.putString("dataurl",mainImgs.dataurl)
            mainImgsFragment.arguments = bundle
            return mainImgsFragment
        }
    }

    inner class StoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val stories_thumbnails = itemView.stories_thumbnails
        private var tag = itemView.tag
        fun bind(storiesArrayList: ArrayList<Stories>){
            if(tag == null) {
                tag = true
                val linearLayoutManager = LinearLayoutManager(mContext)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                stories_thumbnails.layoutManager = linearLayoutManager
                stories_thumbnails.adapter = StoriesAdapter(mContext, storiesArrayList)
            }
        }
    }

    inner class TiktokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tiktok_thumbnails = itemView.tiktok_thumbnails
        private var tag = itemView.tag
        fun bind(tiktokArrayList: ArrayList<Tiktok>){
            if(tag == null) {
                tag = true
                val linearLayoutManager = LinearLayoutManager(mContext)
                linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
                tiktok_thumbnails.layoutManager = linearLayoutManager
                tiktok_thumbnails.adapter = TiktokAdapter(mContext, tiktokArrayList)
            }
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val igvideo_viewpager = itemView.igvideo_viewpager
        private var tag = itemView.tag
        fun bind(igvideoArrayList: ArrayList<Ig>){
            if(tag == null) {
                tag = true
                val igVideoFragmentList: ArrayList<IgVideoFragment> = ArrayList()
                for (i in 0 until igvideoArrayList.size) {
                    igVideoFragmentList.add(getIgVideoFragment(igvideoArrayList[i]))
                }
                val fragmentManager = (mContext as FragmentActivity).supportFragmentManager
                igvideo_viewpager.adapter = IgVideoPagerAdapter(fragmentManager, igVideoFragmentList)
            }
        }
        private fun getIgVideoFragment(ig:Ig):IgVideoFragment{
            val igVideoFragment = IgVideoFragment.newInstance()
            val bundle = Bundle()
            bundle.putParcelable("data",ig)
            igVideoFragment.arguments = bundle
            return igVideoFragment
        }
    }

    inner class IgPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val igpost_date = itemView.igpost_date
        private val igpost_content = itemView.igpost_content
        fun bind(ig: Ig){
            igpost_date.text = ig.date
            igpost_content.setText(ig.content)
            igpost_content.resetState(true)
            igpost_content.drawableExpand = null
            igpost_content.drawableShrink = null
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}