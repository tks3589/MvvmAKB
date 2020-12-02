package com.example.mvvmakb.data

import com.example.mvvmakb.model.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {
    private val BASE_URL = "http://tks3589.ddns.net:1021/akbtp/api/"
    private var service : AkbService

    companion object{
        val instance : RetrofitHelper by lazy {
            RetrofitHelper()
        }
    }

    init {
        val httpClient = OkHttpClient.Builder()
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()

        service = retrofit.create(AkbService::class.java)
    }

    fun getLiveRecord(callback:Callback<List<LiveRecord>>){
        service.getLiveRecord().enqueue(object: Callback<List<LiveRecord>>{
            override fun onFailure(call: Call<List<LiveRecord>>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(
                call: Call<List<LiveRecord>>, response: Response<List<LiveRecord>>
            ) {
                callback.onResponse(call, response)
            }

        })
    }

    fun getMainData(callback: Callback<MainData>){
        service.getMainData().enqueue(object :Callback<MainData>{
            override fun onFailure(call: Call<MainData>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(call: Call<MainData>, response: Response<MainData>) {
                callback.onResponse(call, response)
            }
        })
    }

    fun getMembers(callback: Callback<ArrayList<Member>>){
        service.getMembers().enqueue(object: Callback<ArrayList<Member>>{
            override fun onFailure(call: Call<ArrayList<Member>>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(
                call: Call<ArrayList<Member>>,
                response: Response<ArrayList<Member>>
            ) {
                callback.onResponse(call, response)
            }

        })
    }

    fun getNews(page:Int = 1,callback: Callback<ArrayList<News>>){
        service.getNews(page).enqueue(object : Callback<ArrayList<News>>{
            override fun onFailure(call: Call<ArrayList<News>>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(
                call: Call<ArrayList<News>>,
                response: Response<ArrayList<News>>
            ) {
                callback.onResponse(call, response)
            }

        })
    }

    fun getUpcoming(page:Int = 1,callback: Callback<ArrayList<News>>){
        service.getUpcoming(page).enqueue(object : Callback<ArrayList<News>>{
            override fun onFailure(call: Call<ArrayList<News>>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(
                call: Call<ArrayList<News>>,
                response: Response<ArrayList<News>>
            ) {
                callback.onResponse(call, response)
            }

        })
    }

    fun getIgPost(id:String,date:String,callback: Callback<ArrayList<Ig>>){
        service.getIgPost(id,date).enqueue(object : Callback<ArrayList<Ig>>{
            override fun onFailure(call: Call<ArrayList<Ig>>, t: Throwable) {
                callback.onFailure(call,t)
            }

            override fun onResponse(call: Call<ArrayList<Ig>>, response: Response<ArrayList<Ig>>) {
                callback.onResponse(call,response)
            }

        })
    }

    fun getAbout(callback: Callback<String>){
        service.getAbout().enqueue(object : Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                callback.onFailure(call, t)
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                callback.onResponse(call, response)
            }

        })
    }

}