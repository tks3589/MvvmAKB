package com.example.mvvmakb.viewmodel

import androidx.lifecycle.MutableLiveData
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.MainData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : BaseViewModel(){
    private var item = MutableLiveData<MainData>()
    private var init = MutableLiveData<Boolean>(false)

    fun loadMainData(){
        RetrofitHelper.instance.getMainData(object :Callback<MainData>{
            override fun onFailure(call: Call<MainData>, t: Throwable) {
                mListener.onFailedToReceiveData()
            }

            override fun onResponse(call: Call<MainData>, response: Response<MainData>) {
                if(response.isSuccessful){
                    item.value = response.body()
                    mListener.onReceiveData()
                }else{
                    mListener.onFailedToReceiveData()
                }
            }

        })
    }

    fun getLiveData() : MutableLiveData<MainData>{
        return item
    }

    fun refreshData(){
        init.value = false
        item.value = MainData()
    }

    fun isInit() : Boolean{
        return init.value!!
    }

    fun setInit(status:Boolean){
        init.value = status
    }

}