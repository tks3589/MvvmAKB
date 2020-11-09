package com.example.mvvmakb

interface OnReceiveDataListener {
    fun onReceiveData()
    fun onFailedToReceiveData()
    fun onNoMoreData()
}