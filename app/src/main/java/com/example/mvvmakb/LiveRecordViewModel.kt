package com.example.mvvmakb

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class LiveRecordViewModel : ViewModel() {
    private var items = MutableLiveData<List<LiveRecord>>()
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://tks3589.ddns.net:1021/akbtp/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val service = retrofit.create(AkbService::class.java)

    fun getLiveData() : MutableLiveData<List<LiveRecord>>{
        service.liverecord().enqueue(object: Callback<List<LiveRecord>>{
            override fun onFailure(call: Call<List<LiveRecord>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<LiveRecord>>,
                response: Response<List<LiveRecord>>
            ) {
                if(response.isSuccessful){
                    items.value = response.body()
                }
            }


        })
        return items
    }

    fun loadMoreData(){

    }
}

interface AkbService{
    @GET("liverecord")
    fun liverecord():Call<List<LiveRecord>>

}