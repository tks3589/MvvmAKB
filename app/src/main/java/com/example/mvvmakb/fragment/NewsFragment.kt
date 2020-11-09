package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.EventsAdapter
import kotlinx.android.synthetic.main.fragment_news.view.*

class NewsFragment : Fragment() {
    private lateinit var eventsAdapter: EventsAdapter

    companion object{
        val instance : NewsFragment by lazy {
            NewsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news,container,false)
        eventsAdapter = EventsAdapter(view.context,"news")
        view.news_listView.adapter = eventsAdapter

        return view
    }
}