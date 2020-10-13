package com.example.mvvmakb.data

import com.example.mvvmakb.model.LiveRecord
import com.example.mvvmakb.model.MainData
import retrofit2.Call
import retrofit2.http.GET

interface AkbService {
    @GET("liverecord")
    fun getLiveRecord(): Call<List<LiveRecord>>

    @GET("getMainData3")
    fun getMainData(): Call<MainData>
}