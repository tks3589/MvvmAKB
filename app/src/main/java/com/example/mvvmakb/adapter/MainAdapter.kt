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
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.mvvmakb.R
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.fragment.IgVideoFragment
import com.example.mvvmakb.fragment.MainImgsFragment
import com.example.mvvmakb.model.*
import com.example.mvvmakb.view.ScaleInTransformer
import com.example.mvvmakb.viewmodel.IgPostViewModel
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
    private lateinit var igViewModel: IgPostViewModel
    private lateinit var mContext: Context
    private var mainImgsArrayList: ArrayList<MainImgs> = ArrayList()
    private var storiesArrayList: ArrayList<Stories> = ArrayList()
    private var tiktokArrayList: ArrayList<Tiktok>  = ArrayList()
    private var igvideoArrayList: ArrayList<Ig>  = ArrayList()
    private var igpostArrayList: ArrayList<Ig>  = ArrayList()
    private lateinit var mListener: OnReceiveDataListener
    private var LAST_ONE = false
    private val id = "akb48teamtp"

    constructor(context: Context):this(){
        mContext = context as FragmentActivity
        mViewModel = ViewModelProvider(context).get(MainViewModel::class.java)
        igViewModel = ViewModelProvider(context).get(IgPostViewModel::class.java)

        mViewModel.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {
                mListener.onReceiveData()
                if(!mViewModel.isInit()){
                    mViewModel.setInit(true)
                }
            }

            override fun onFailedToReceiveData() {
                mListener.onFailedToReceiveData()
            }

            override fun onNoMoreData() {
            }

        })
        mViewModel.getLiveData().observe(context,androidx.lifecycle.Observer {
            mainImgsArrayList = it.mainImgsArrayList
            storiesArrayList = it.storiesArrayList
            tiktokArrayList = it.tiktokArrayList
            igvideoArrayList = it.igVideoArrayList
            igpostArrayList = it.igPostArrayList
            notifyDataSetChanged()
        })

        igViewModel.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {
                mListener.onReceiveData()
            }

            override fun onFailedToReceiveData() {
                mListener.onFailedToReceiveData()
            }

            override fun onNoMoreData() {
                mListener.onNoMoreData()
                LAST_ONE = true
            }

        })
        igViewModel.getLiveData().observe(context,androidx.lifecycle.Observer {
            igpostArrayList.addAll(it)
            notifyDataSetChanged()
        })

        refresh()
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
        else if(igpostArrayList.size > position && position >= 4)
            VIEW_TYPE_IG_ITEM
        else if(LAST_ONE)
            VIEW_TYPE_DONE
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

        fun bind(mainImgsArrayList: ArrayList<MainImgs>){ //注意bind執行次數 getItemCount會從0開始
            mainimgs_linear_h.removeAllViews()

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
        fun bind(storiesArrayList: ArrayList<Stories>){
            val linearLayoutManager = LinearLayoutManager(mContext)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            stories_thumbnails.layoutManager = linearLayoutManager
            stories_thumbnails.adapter = StoriesAdapter(mContext, storiesArrayList)
        }
    }

    inner class TiktokViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tiktok_thumbnails = itemView.tiktok_thumbnails
        fun bind(tiktokArrayList: ArrayList<Tiktok>){
            val linearLayoutManager = LinearLayoutManager(mContext)
            linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
            tiktok_thumbnails.layoutManager = linearLayoutManager
            tiktok_thumbnails.adapter = TiktokAdapter(mContext, tiktokArrayList)
        }
    }

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val igvideo_viewpager = itemView.igvideo_viewpager
        fun bind(igvideoArrayList: ArrayList<Ig>){
            val igVideoFragmentList: ArrayList<IgVideoFragment> = ArrayList()
            for (i in 0 until igvideoArrayList.size) {
                igVideoFragmentList.add(getIgVideoFragment(igvideoArrayList[i]))
            }
            val fragmentActivity = (mContext as FragmentActivity)
            igvideo_viewpager.apply {
                offscreenPageLimit = 1
                val recyclerView = getChildAt(0) as RecyclerView
                recyclerView.apply {
                    val padding = resources.getDimensionPixelOffset(R.dimen.dp_10) +
                            resources.getDimensionPixelOffset(R.dimen.dp_10)
                    setPadding(padding, 0, padding, 0)
                    clipToPadding = false
                }
            }
            val compositePageTransformer = CompositePageTransformer()
            compositePageTransformer.addTransformer(ScaleInTransformer())
            compositePageTransformer.addTransformer(MarginPageTransformer(mContext.resources.getDimension(R.dimen.dp_10).toInt()))
            igvideo_viewpager.setPageTransformer(compositePageTransformer)
            igvideo_viewpager.adapter = IgVideoPagerAdapter(fragmentActivity, igVideoFragmentList)
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
        private val igpost_viewpager = itemView.igpost_viewpager
        private val igpost_linear_h = itemView.igpost_linear_content
        private val igpost_content = itemView.igpost_content
        fun bind(ig: Ig){
            igpost_date.text = ig.date
            //igpost_viewpager.adapter = IgPostPagerAdapter(mContext,ig.imgurls)
            //igpost_linear_h
            igpost_content.setText(ig.content)
            igpost_content.resetState(true)
            igpost_content.drawableExpand = null
            igpost_content.drawableShrink = null
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun refresh(){ //recyclerview記錄滑動位置
        mViewModel.refreshData()
        mViewModel.loadMainData()
        LAST_ONE = false
    }

    fun isInit():Boolean{
        return mViewModel.isInit()
    }

    fun loadIgPost(){
        val date = igpostArrayList[igpostArrayList.size-1].date //viewmodel -> get set  date
        igViewModel.loadIgPost(id,date)
    }

    fun setListener(onReceiveDataListener: OnReceiveDataListener){
        mListener = onReceiveDataListener
    }
}