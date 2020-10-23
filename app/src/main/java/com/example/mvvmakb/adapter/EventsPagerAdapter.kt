package com.example.mvvmakb.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mvvmakb.R
import com.example.mvvmakb.fragment.NewsFragment
import com.example.mvvmakb.fragment.UpcomingFragment

class EventsPagerAdapter(context:Context,manager: FragmentManager) : FragmentPagerAdapter(manager) {
    private val mContext = context
    override fun getItem(position: Int): Fragment {
        return if(position == 0)
            NewsFragment.instance
        else
            UpcomingFragment.instance
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getStringArray(R.array.Events)[position]
    }
}