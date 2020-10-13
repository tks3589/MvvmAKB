package com.example.mvvmakb.data

import com.example.mvvmakb.model.LiveRecord
import com.example.mvvmakb.model.MainData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

}