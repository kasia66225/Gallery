package com.example.gallery

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_full_image.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.FieldPosition
import java.util.*

class MainActivity : AppCompatActivity() {
    var images = ArrayList<Image>()
    internal var imagePath: String? = ""
    val REQUEST_PERM_WRITE_STORAGE = 102
    private val CAPTURE_PHOTO = 104

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonAddPhoto.setOnClickListener{
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.CAMERA), 1)
                }

            }
            if (ActivityCompat.checkSelfPermission(applicationContext,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this@MainActivity,
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERM_WRITE_STORAGE)

            } else {
                takePhoto()
            }
        }
    }

    fun takePhoto() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAPTURE_PHOTO)

    }

    override fun onStart() {
        super.onStart()
        refreshArrayList()

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, returnIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, returnIntent)
        if (requestCode == 123) {

        }

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {

                CAPTURE_PHOTO -> {

                    val capturedBitmap = returnIntent!!.extras!!.get("data") as Bitmap
                    Toast.makeText(this,"saved",Toast.LENGTH_LONG).show()
                    images.add(Image())
                    saveImage(capturedBitmap,images.size)
                }
                else -> {
                }
            }

        }

    }

    private fun refreshArrayList(){

        gridView.adapter = ImageAdapter(this, images)

        gridView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val intent = Intent(this@MainActivity, FullImage::class.java)

                val stream = ByteArrayOutputStream()
                //images[position].bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                //intent.putExtra("bitmap",byteArray)
                intent.putExtra("path",images[position].path)

                startActivityForResult(intent, 123)

            }
        }
    }

    private fun saveImage(finalBitmap: Bitmap, position: Int) {

        val root = Environment.getExternalStorageDirectory().toString()
        val myDir = File(root + "/capture_photo")
        myDir.mkdirs()
        val generator = Random()
        var n = 10000
        n = generator.nextInt(n)
        val OutletFname = "Image-$n.jpg"
        val file = File(myDir, OutletFname)
        if (file.exists()) file.delete()
        try {
            val out = FileOutputStream(file)
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            imagePath = file.absolutePath

            out.flush()
            out.close()

        } catch (e: Exception) {
            e.printStackTrace()

        }

        val intent = Intent(this@MainActivity, FullImage::class.java)
        images[position-1].path = imagePath.toString()

       // val stream = ByteArrayOutputStream()
       // images[position].bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
      //  val byteArray = stream.toByteArray()
      //  intent.putExtra("bitmap",byteArray)
       // intent.putExtra("path",imagePath)

       // startActivityForResult(intent, 123)
    }

}
