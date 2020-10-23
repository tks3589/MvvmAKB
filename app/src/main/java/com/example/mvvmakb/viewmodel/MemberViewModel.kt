package com.example.mvvmakb.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmakb.data.RetrofitHelper
import com.example.mvvmakb.model.Member
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberViewModel : ViewModel() {
    private var items = MutableLiveData<ArrayList<Member>>()

    fun getLiveData() : MutableLiveData<ArrayList<Member>>{
        RetrofitHelper.instance.getMembers(object :Callback<ArrayList<Member>>{
            override fun onFailure(call: Call<ArrayList<Member>>, t: Throwable) {

            }

            override fun onResponse(
                call: Call<ArrayList<Member>>,
                response: Response<ArrayList<Member>>
            ) {
                if(response.isSuccessful){
                    items.value = response.body()
                }else{

                }
            }

        })

        return items
    }
}