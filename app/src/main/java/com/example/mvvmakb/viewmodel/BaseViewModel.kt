package com.example.mvvmakb.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mvvmakb.OnReceiveDataListener

open class BaseViewModel : ViewModel(){
    protected lateinit var mListener: OnReceiveDataListener

    fun setListener(onReceiveDataListener: OnReceiveDataListener){
        mListener = onReceiveDataListener
    }
}