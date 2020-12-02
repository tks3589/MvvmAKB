package com.example.mvvmakb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmakb.R
import kotlinx.android.synthetic.main.activity_appbarlayout.*

class SocialRankActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_social_rank)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "社群資料排名"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}