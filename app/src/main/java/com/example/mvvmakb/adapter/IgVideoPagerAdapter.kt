package com.example.mvvmakb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.mvvmakb.fragment.IgVideoFragment

class IgVideoPagerAdapter(manager: FragmentManager,igVideoFragmentList: ArrayList<IgVideoFragment>): FragmentStatePagerAdapter(manager){
    private val igVideoFragmentList = igVideoFragmentList
    override fun getItem(position: Int): Fragment {
        return igVideoFragmentList[position]
    }

    override fun getCount(): Int {
        return igVideoFragmentList.size
    }
}