package com.example.mvvmakb.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.mvvmakb.R
import com.example.mvvmakb.model.Ig
import kotlinx.android.synthetic.main.item_main_igvideo_viewpager.view.*

class IgVideoFragment: Fragment() {
    companion object{
        fun newInstance() = IgVideoFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.item_main_igvideo_viewpager, container, false)
        val imageview = view.igvideo_imageview
        imageview.scaleType = ImageView.ScaleType.CENTER_CROP
        arguments?.getParcelable<Ig>("data")?.let { it ->
            val data = processData(it)
            val imgUrl = data[0]
            val videoUrl = data[1]
            context?.let {
                Glide.with(it)
                    .load(imgUrl)
                    .transform(CenterCrop(), RoundedCorners(10))
                    .into(imageview)
            }
        }
        return view
    }

    private fun processData(ig: Ig): ArrayList<String>{
        var preview_url = ""
        var video_url = ""
        var result = arrayListOf<String>()
        for(url in ig.imgurls){
            var tmp = url.replace('\'', ' ').trim()
            tmp = tmp.substring(tmp.indexOf(":") + 1)
            if (tmp.indexOf(".jpg") != -1)
                preview_url = tmp
            else
                video_url = tmp
        }
        result.add(preview_url)
        result.add(video_url)

        return result
    }
}