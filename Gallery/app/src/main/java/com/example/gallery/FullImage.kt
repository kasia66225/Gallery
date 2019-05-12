package com.example.gallery

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_full_image.*
import kotlinx.android.synthetic.main.activity_main.*
import android.graphics.BitmapFactory
import android.os.Environment
import java.io.File
import java.io.FileInputStream


class FullImage : AppCompatActivity (), RatingBar.OnRatingBarChangeListener {

    var img : Int = 0
    var bitmap : Bitmap? = null
    var byteArray : ByteArray?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        val bundle = intent.extras

       // byteArray = bundle.getByteArray("bitmap")
        var bytePath = bundle.getString("path")

        val myBitmap = BitmapFactory.decodeFile(bytePath)

        //bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size)
        fullImage.setImageBitmap(myBitmap)


        fullImage.scaleType = ImageView.ScaleType.CENTER_CROP

    }


    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {

    }

}