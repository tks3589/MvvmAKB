package com.example.mvvmakb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvmakb.fragment.IgVideoFragment

class IgVideoPagerAdapter(fragmentActivity: FragmentActivity,igVideoFragmentList: ArrayList<IgVideoFragment>): FragmentStateAdapter(fragmentActivity){
    private val igVideoFragmentList = igVideoFragmentList

    override fun getItemCount(): Int {
        return igVideoFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return igVideoFragmentList[position]
    }
}