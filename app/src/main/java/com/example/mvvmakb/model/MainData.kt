package com.example.mvvmakb.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class MainData (
    @SerializedName("mainImgs")
    val mainImgsArrayList: ArrayList<MainImgs> = ArrayList(),

    @SerializedName("stories")
    val storiesArrayList: ArrayList<Stories> = ArrayList(),

    @SerializedName("tiktok")
    val tiktokArrayList: ArrayList<Tiktok> = ArrayList(),

    @SerializedName("igvideos")
    val igVideoArrayList: ArrayList<Ig> = ArrayList(),

    @SerializedName("igposts")
    val igPostArrayList: ArrayList<Ig> = ArrayList()
)