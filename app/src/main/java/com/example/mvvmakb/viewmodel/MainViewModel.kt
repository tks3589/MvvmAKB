package com.example.mvvmakb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.MainData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){
    private var item = MutableLiveData<MainData>()

    fun getLiveData() : MutableLiveData<MainData>{
        RetrofitHelper.instance.getMainData(object :Callback<MainData>{
            override fun onFailure(call: Call<MainData>, t: Throwable) {

            }

            override fun onResponse(call: Call<MainData>, response: Response<MainData>) {
                if(response.isSuccessful){
                    item.value = response.body()
                }else{

                }
            }

        })
        return item
    }

}