package com.example.gallery

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.activity_full_image.*
import java.util.*
import kotlin.collections.ArrayList

class ImageAdapter(private val context: Activity, private val images : ArrayList<Image>)  : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.activity_image,null,true)
        val imageView = rowView.findViewById(R.id.icon) as ImageView

        val myBitmap = BitmapFactory.decodeFile(images[position].path)

        imageView.setImageBitmap(myBitmap)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
//        imageView.layoutParams = LinearLayout.LayoutParams(240, 240)


        return imageView
    }

    override fun getItem(position: Int): Any {
        return images.get(position)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return images.size
    }
}