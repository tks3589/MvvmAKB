package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmakb.R
import com.example.mvvmakb.viewmodel.UpcomingViewModel

class UpcomingFragment : Fragment(){
    private lateinit var upcomingViewModel: UpcomingViewModel

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
        return inflater.inflate(R.layout.fragment_upcoming,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        upcomingViewModel = ViewModelProvider(this).get(UpcomingViewModel::class.java)
    }
}