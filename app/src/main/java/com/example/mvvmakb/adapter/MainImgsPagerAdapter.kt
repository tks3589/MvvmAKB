package com.example.mvvmakb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mvvmakb.fragment.MainImgsFragment

class MainImgsPagerAdapter(manager: FragmentManager,mainImgsFragmentList: ArrayList<MainImgsFragment>) : FragmentStatePagerAdapter(manager){
    private val mainImgsFragmentList = mainImgsFragmentList

    override fun getItem(position: Int): Fragment {
        return mainImgsFragmentList[position]
    }

    override fun getCount(): Int {
        return mainImgsFragmentList.size
    }

}
