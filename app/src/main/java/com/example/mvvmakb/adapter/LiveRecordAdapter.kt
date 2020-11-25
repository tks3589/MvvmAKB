package com.example.mvvmakb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.R
import com.example.mvvmakb.model.LiveRecord
import com.example.mvvmakb.viewmodel.LiveRecordViewModel
import kotlinx.android.synthetic.main.item_live_record.view.*

class LiveRecordAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_DONE = 2
    private lateinit var mContext: Context
    private lateinit var mViewModel: LiveRecordViewModel
    private lateinit var mListener: OnReceiveDataListener
    private var liveRecordList: List<LiveRecord> = listOf()
    private var empty = false

    constructor(context: Context):this(){
        mContext = context as FragmentActivity
        mViewModel = ViewModelProvider(context).get(LiveRecordViewModel::class.java)
        mViewModel.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {
                mListener.onReceiveData()
            }

            override fun onFailedToReceiveData() {
                mListener.onFailedToReceiveData()
            }

            override fun onNoMoreData() {
            }

        })
        mViewModel.getLiveData().observe(context,androidx.lifecycle.Observer{
            liveRecordList = it
            notifyDataSetChanged()
        })
        refresh()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_ITEM -> LiveRecordHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_live_record,parent,false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false))
            else -> DoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_done,parent,false))
        }
    }

    override fun getItemCount(): Int {
        if(liveRecordList.isEmpty())
            return 1
        return liveRecordList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            liveRecordList.isNotEmpty() -> {
                VIEW_TYPE_ITEM
            }
            empty ->{
                VIEW_TYPE_DONE
            }
            else -> {
                VIEW_TYPE_LOADING
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is LiveRecordHolder)
            holder.bind(liveRecordList[position])
    }
    inner class LiveRecordHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val liverecord_imgview = itemView.liverecord_imgview
        private val liverecord_name = itemView.liverecord_name
        private val liverecord_time = itemView.liverecord_time
        private val liverecord_livetag = itemView.liverecord_livetag

        fun bind(liveRecord: LiveRecord){
            Glide.with(mContext).load(liveRecord.imgurl).into(liverecord_imgview)
            liverecord_name.text = liveRecord.cname
            liverecord_time.text = "開播時間：${formatDate(liveRecord.date)} ${formatTime(liveRecord.livetime)} ~ ${formatTime(liveRecord.offlivetime)}"
            if(!liveRecord.islive)
                liverecord_livetag.visibility = View.GONE
            else
                liverecord_livetag.visibility = View.VISIBLE
        }

        private fun formatDate(date:String):String{
            return date.substring(0,4)+"/"+date.substring(4,6)+"/"+date.substring(6,8)
        }

        private fun formatTime(time:String):String{
            return time.substring(0,2)+":"+time.substring(2,4)
        }
    }
    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun refresh(){
        mViewModel.refreshData()
        mViewModel.loadLiveRecordData()
    }

    fun setListener(onReceiveDataListener: OnReceiveDataListener){
        mListener = onReceiveDataListener
    }

}