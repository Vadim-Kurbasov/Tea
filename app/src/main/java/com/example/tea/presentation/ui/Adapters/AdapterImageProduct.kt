package com.example.tea.presentation.ui.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.viewpager.widget.PagerAdapter
import com.example.tea.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class AdapterImageProduct (var list: List<String>, var ctx: Context) : PagerAdapter() {

    private lateinit var ImgList:List<Int>
    lateinit var layoutInflater:LayoutInflater
    lateinit var context:Context

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view.equals(`object`)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(ctx)
        var view = layoutInflater.inflate(R.layout.item1,container,false)
        val img = view.findViewById<ImageView>(R.id.simpleimg1)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBarShowImage)

        Picasso.get()
            .load(list[position])
            // .resize(130, 130)
            // .into(img)
            .into(img, object : Callback {
                override fun onSuccess() {
                    if (progressBar != null) {
                        progressBar.setVisibility(View.GONE)
                    }
                }
                override fun onError(e: Exception?) {
                    TODO("Not yet implemented")
                }
            })
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}