package com.example.tea.presentation.ui.Adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.tea.R

public class AdapterImageAdmin(var list: List<Uri>, var ctx: Context) : PagerAdapter() {

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
        val view = layoutInflater.inflate(R.layout.item,container,false)
        val img = view.findViewById<ImageView>(R.id.simpleimg)
        img.setImageURI(list[position])
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}