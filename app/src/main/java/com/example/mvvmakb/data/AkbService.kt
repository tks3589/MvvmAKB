package com.example.mvvmakb.data

import com.example.mvvmakb.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AkbService {
    @GET("liverecord")
    fun getLiveRecord(): Call<List<LiveRecord>>

    @GET("getMainData3")
    fun getMainData(): Call<MainData>

    @GET("members2/3")
    fun getMembers(): Call<ArrayList<Member>>

    @GET("news3/{page}")
    fun getNews(
        @Path("page")
        page: Int
    ): Call<ArrayList<News>>

    @GET("upcoming3/{page}")
    fun getUpcoming(
        @Path("page")
        page: Int
    ): Call<ArrayList<News>>

    @GET("ig/{id}/{date}")
    fun getIgPost(
        @Path("id")
        id: String,
        @Path("date")
        date: String
    ): Call<ArrayList<Ig>>

    @GET("about2")
    fun getAbout(): Call<String>
}