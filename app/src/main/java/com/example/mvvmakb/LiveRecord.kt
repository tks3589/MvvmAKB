package com.example.mvvmakb

import android.os.Parcelable

data class LiveRecord(
    val id: Int = 0,
    val date: String = "",
    val livetime: String = "",
    val offlivetime: String = "",
    val islive: Boolean = false,
    val cname: String = "",
    val imgurl: String = "",
    val dataurl: String = "",
    val hotvalue: String = "",
    val vimgurl: String = "",
    val videourl: String = "",
    val title: String = "",
    val duration: String = ""
)