package com.example.mvvmakb.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmakb.R
import kotlinx.android.synthetic.main.activity_appbarlayout.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "設定"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}