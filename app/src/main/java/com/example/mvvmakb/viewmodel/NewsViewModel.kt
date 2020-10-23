package com.example.mvvmakb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel(){
    private var items = MutableLiveData<ArrayList<News>>()

    fun getLiveData() : MutableLiveData<ArrayList<News>>{
        RetrofitHelper.instance.getNews(1,object :Callback<ArrayList<News>>{
            override fun onFailure(call: Call<ArrayList<News>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<News>>,
                response: Response<ArrayList<News>>
            ) {
                items.value = response.body()
            }

        })

        return items
    }
}