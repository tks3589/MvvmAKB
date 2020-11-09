package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.EventsAdapter
import kotlinx.android.synthetic.main.fragment_upcoming.view.*

class UpcomingFragment : Fragment(){
    private lateinit var eventsAdapter: EventsAdapter

    companion object{
        val instance : UpcomingFragment by lazy {
            UpcomingFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_upcoming,container,false)
        eventsAdapter = EventsAdapter(view.context,"upcoming")
        view.upcoming_listView.adapter = eventsAdapter

        return view
    }
}