package com.example.mvvmakb.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.mvvmakb.R
import kotlinx.android.synthetic.main.item_main_imgs_viewpager.view.*

class MainImgsFragment : Fragment(){
    companion object{
        fun newInstance() = MainImgsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_main_imgs_viewpager, container, false)
        var bundle = arguments
        val imgurl = bundle?.getString("imgurl")
        val dataurl = bundle?.getString("dataurl")
        val imageview = view.mainimgs_imageview
        context?.let {
            Glide.with(it).load(imgurl).into(imageview)
            imageview.setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(dataurl)))
            }
        }

        return view
    }
}