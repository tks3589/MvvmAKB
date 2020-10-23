package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.EventsPagerAdapter
import kotlinx.android.synthetic.main.fragment_events.view.*

class EventsFragment : Fragment(){

    companion object{
        val instance : EventsFragment by lazy {
            EventsFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_events, container, false)
        val events_viewpager = view.events_viewpager
        context?.let {
            events_viewpager.adapter = EventsPagerAdapter(it,(context as FragmentActivity).supportFragmentManager)
            view.events_tab.setupWithViewPager(events_viewpager)
        }
        return view
    }
}