package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.MainAdapter
import kotlinx.android.synthetic.main.fragment_main.view.*

class MainFragment : Fragment(){

    companion object{
        val instance : MainFragment by lazy {
            MainFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(context)
        view.recyclerView.adapter = context?.let { MainAdapter(it) }
        return view
    }
}