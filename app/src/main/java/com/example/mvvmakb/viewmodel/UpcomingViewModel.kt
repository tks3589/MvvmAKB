package com.example.mvvmakb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpcomingViewModel : BaseViewModel(){
    private var items = MutableLiveData<ArrayList<News>>()
    private var init = MutableLiveData<Boolean>(false)
    private var page = MutableLiveData<Int>(1)
    private var tmp = ArrayList<News>()

    fun loadUpcomingData(page:Int) {
        RetrofitHelper.instance.getUpcoming(page,object :Callback<ArrayList<News>>{
            override fun onFailure(call: Call<ArrayList<News>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<ArrayList<News>>,
                response: Response<ArrayList<News>>
            ) {
                if(response.isSuccessful) {
                    var data = response.body()
                    tmp.addAll(data!!)
                    items.value = tmp
                    mListener.onReceiveData()
                    if(data.size == 0)
                        mListener.onNoMoreData()
                }else{
                    mListener.onFailedToReceiveData()
                }
            }

        })
    }

    fun getLiveData() : MutableLiveData<ArrayList<News>>{
        return items
    }

    fun addPage(){
        page.value = getPage()+1
    }

    fun getPage() : Int{
        return page.value!!
    }

    fun refreshData(){
        page.value = 1
        init.value = false
        tmp = arrayListOf()
        items.value = arrayListOf()
    }

    fun isInit() : Boolean{
        return init.value!!
    }

    fun setInit(status:Boolean){
        init.value = status
    }


}