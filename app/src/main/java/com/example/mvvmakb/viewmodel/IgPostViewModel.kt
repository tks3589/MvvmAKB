package com.example.mvvmakb.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.Ig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IgPostViewModel : BaseViewModel(){
    private var item = MutableLiveData<ArrayList<Ig>>()

    fun loadIgPost(id:String,date:String){
        RetrofitHelper.instance.getIgPost(id,date,object :Callback<ArrayList<Ig>>{
            override fun onFailure(call: Call<ArrayList<Ig>>, t: Throwable) {
                mListener.onFailedToReceiveData()
            }

            override fun onResponse(call: Call<ArrayList<Ig>>, response: Response<ArrayList<Ig>>) {
                if(response.isSuccessful){
                    var data = response.body()
                    item.value = data
                    mListener.onReceiveData()
                    if(data?.size == 0)
                        mListener.onNoMoreData()
                }else{
                    mListener.onFailedToReceiveData()
                }
            }

        })
    }

    fun getLiveData() : MutableLiveData<ArrayList<Ig>>{
        return item
    }




}