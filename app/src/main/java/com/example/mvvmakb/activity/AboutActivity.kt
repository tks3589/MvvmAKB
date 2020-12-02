package com.example.mvvmakb.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmakb.OnReceiveDataListener
import com.example.mvvmakb.R
import com.example.mvvmakb.viewmodel.AboutViewModel
import kotlinx.android.synthetic.main.activity_about.*
import kotlinx.android.synthetic.main.activity_appbarlayout.*

class AboutActivity :AppCompatActivity(){
    private lateinit var mViewModel: AboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.title = "關於我們"
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
        mViewModel = ViewModelProvider(this).get(AboutViewModel::class.java)
        mViewModel.setListener(object :OnReceiveDataListener{
            override fun onReceiveData() {

            }

            override fun onFailedToReceiveData() {
                Toast.makeText(this@AboutActivity,"壞了", Toast.LENGTH_SHORT).show()
            }

            override fun onNoMoreData() {
            }

        })
        mViewModel.getLiveData().observe(this,androidx.lifecycle.Observer {
            about_textview.text = it
        })

        mViewModel.loadAboutData()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}