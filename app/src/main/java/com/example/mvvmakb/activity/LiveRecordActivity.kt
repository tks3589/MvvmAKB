package com.example.mvvmakb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.LiveRecordAdapter
import kotlinx.android.synthetic.main.activity_live_record.*

class LiveRecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_record)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = LiveRecordAdapter(this)
    }
}