package com.example.mvvmakb

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_akb.view.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = AkbAdapter(this)

    }

}

class AkbAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1
    private val VIEW_TYPE_DONE = 2
    private lateinit var mContext: Context
    private lateinit var mViewModel: LiveRecordViewModel
    private var liveRecordList: List<LiveRecord> = listOf()
    private var empty = false

    constructor(context:Context):this(){
        mContext = context as FragmentActivity
        mViewModel = ViewModelProvider(context).get(LiveRecordViewModel::class.java)
        mViewModel.getLiveData().observe(context,androidx.lifecycle.Observer{
            liveRecordList = it
            notifyDataSetChanged()
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VIEW_TYPE_ITEM -> AkbViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_akb,parent,false))
            VIEW_TYPE_LOADING -> LoadingViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_loading,parent,false))
            else -> DoneViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_done,parent,false))
        }
    }

    override fun getItemCount(): Int {
        if(liveRecordList.isEmpty()||empty)
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
        if(holder is AkbViewHolder)
            holder.bind(liveRecordList[position])
    }
    inner class AkbViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val liverecord_imgview = itemView.liverecord_imgview
        private val liverecord_name = itemView.liverecord_name
        private val liverecord_time = itemView.liverecord_time
        private val liverecord_livetag = itemView.liverecord_livetag

        fun bind(liveRecord: LiveRecord){
            Glide.with(mContext).load(liveRecord.imgurl).into(liverecord_imgview)
            liverecord_name.text = liveRecord.cname
            liverecord_time.text = "開播時間：${liveRecord.date} ${liveRecord.livetime}～${liveRecord.offlivetime}"
            if(!liveRecord.islive)
                liverecord_livetag.visibility = View.GONE
            else
                liverecord_livetag.visibility = View.VISIBLE
        }
    }
    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    inner class DoneViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}