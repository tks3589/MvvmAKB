package com.example.mvvmakb.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ig(
    val date: String,

    val memberid: String,

    val url: String,

    @SerializedName("data")
    val imgurls: ArrayList<String>,

    val content: String,

    val type: String
):Parcelable