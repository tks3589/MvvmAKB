package com.example.mvvmakb.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import androidx.fragment.app.FragmentActivity
import com.example.mvvmakb.R
import com.example.mvvmakb.model.Member
import kotlinx.android.synthetic.main.expandablelist_group.view.*
import kotlinx.android.synthetic.main.expandablelist_item.view.*

class MemberExpandableListAdapter() : BaseExpandableListAdapter() {
    private lateinit var mContext: Context
    private var groupList: ArrayList<String> = ArrayList()
    private var memberItemList: ArrayList<ArrayList<Member>> = ArrayList()

    constructor(context: Context,memberItemList: ArrayList<Member>):this(){
        mContext = context as FragmentActivity
        setData(memberItemList)
    }

    private fun setData(memberItemList: ArrayList<Member>){
        groupList.add("正式生")
        groupList.add("研究生")
        groupList.add("畢業生")
        var itemGridList0:ArrayList<Member> = ArrayList()
        var itemGridList1:ArrayList<Member> = ArrayList()
        var itemGridList2:ArrayList<Member> = ArrayList()
        for (member in memberItemList){
            when(member.type){
                0 -> itemGridList0.add(member)
                1 -> itemGridList1.add(member)
                2 -> itemGridList2.add(member)
            }
        }
        this.memberItemList.add(itemGridList0)
        this.memberItemList.add(itemGridList1)
        this.memberItemList.add(itemGridList2)
    }


    override fun getGroup(groupPosition: Int): Any {
        return groupList[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: View.inflate(mContext,R.layout.expandablelist_group, null)
        when(isExpanded){
            true -> view.iv_group.setImageResource(R.drawable.ic_open_white)
            false -> view.iv_group.setImageResource(R.drawable.ic_close_white)
        }
        view.tv_group.text = groupList[groupPosition]

        return view
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 1
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return memberItemList[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val view = convertView ?: View.inflate(mContext,R.layout.expandablelist_item, null)
        view.expandablelist_gridview.adapter = MemberGridViewAdapter(mContext,memberItemList[groupPosition])
        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return groupList.size
    }
}