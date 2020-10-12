package com.example.mvvmakb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.LiveRecord
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LiveRecordViewModel : ViewModel() {
    private var items = MutableLiveData<List<LiveRecord>>()

    fun getLiveData() : MutableLiveData<List<LiveRecord>>{
        RetrofitHelper.instance.getLiveRecord(object :Callback<List<LiveRecord>>{
            override fun onFailure(call: Call<List<LiveRecord>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<List<LiveRecord>>,
                response: Response<List<LiveRecord>>
            ) {
                if(response.isSuccessful){
                    items.value = response.body()
                }else{

                }
            }

        })
        return items
    }

    fun loadMoreData(){

    }
}