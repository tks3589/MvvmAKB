package com.example.mvvmakb.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mvvmakb.data.RetrofitHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutViewModel: BaseViewModel() {
    private var item = MutableLiveData<String>()

    fun loadAboutData(){
        RetrofitHelper.instance.getAbout(object :Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                //Log.d("throwable",t.message)
                mListener.onFailedToReceiveData()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    item.value = response.body()
                    mListener.onReceiveData()
                }else{
                    mListener.onFailedToReceiveData()
                }
            }

        })
    }

    fun getLiveData() : MutableLiveData<String>{
        return item
    }
}