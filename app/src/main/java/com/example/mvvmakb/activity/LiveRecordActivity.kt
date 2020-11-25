package com.example.mvvmakb.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.LiveRecordAdapter
import kotlinx.android.synthetic.main.activity_appbarlayout.*
import kotlinx.android.synthetic.main.activity_live_record.*

class LiveRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_record)
        recyclerview.layoutManager = LinearLayoutManager(this)
        val adapter = LiveRecordAdapter(this)
        adapter.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {
            }

            override fun onFailedToReceiveData() {
                Toast.makeText(this@LiveRecordActivity,"壞了", Toast.LENGTH_SHORT).show()
            }

            override fun onNoMoreData() {
            }

        })
        recyclerview.adapter = adapter
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "直播紀錄"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        swipeRefreshLayout.apply {
            setOnRefreshListener {
                adapter.refresh()
                isRefreshing = false
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}