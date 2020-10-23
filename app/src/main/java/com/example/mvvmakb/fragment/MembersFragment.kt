package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmakb.R
import com.example.mvvmakb.adapter.MemberExpandableListAdapter
import com.example.mvvmakb.viewmodel.MemberViewModel
import kotlinx.android.synthetic.main.fragment_members.view.*

class MembersFragment : Fragment(){
    private lateinit var mViewModel: MemberViewModel
    companion object{
        val instance : MembersFragment by lazy {
            MembersFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_members, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mViewModel = ViewModelProvider(this).get(MemberViewModel::class.java)
        mViewModel.getLiveData().observe(viewLifecycleOwner,androidx.lifecycle.Observer {
            context?.let { it2 ->
                val members_exview = view!!.members_exview
                members_exview.setAdapter(MemberExpandableListAdapter(it2,it))
                members_exview.setGroupIndicator(null)
                members_exview.expandGroup(0)
                members_exview.setOnGroupExpandListener { groupPosition: Int ->
                    when (groupPosition) {
                        0 -> {
                            members_exview.collapseGroup(1)
                            members_exview.collapseGroup(2)
                        }
                        1 -> {
                            members_exview.collapseGroup(0)
                            members_exview.collapseGroup(2)
                        }
                        else -> {
                            members_exview.collapseGroup(0)
                            members_exview.collapseGroup(1)
                        }
                    }
                }
            }
        })
    }
}