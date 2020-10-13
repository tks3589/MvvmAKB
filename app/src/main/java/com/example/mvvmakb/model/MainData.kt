package com.example.mvvmakb.model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

data class MainData (
    @SerializedName("mainImgs")
    val mainImgsArrayList: ArrayList<MainImgs>,

    @SerializedName("stories")
    val storiesArrayList: ArrayList<Stories>,

    @SerializedName("tiktok")
    val tiktokArrayList: ArrayList<Tiktok>,

    @SerializedName("igvideos")
    val igVideoArrayList: ArrayList<Ig>,

    @SerializedName("igposts")
    val igPostArrayList: ArrayList<Ig>
)