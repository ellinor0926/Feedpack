package com.feedpack.androidapp

import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_investigation.*

class InvestigationActivity : AppCompatActivity() {

            private val CAMERA = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_investigation)

        submitInvestigationBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Thank you!", Toast.LENGTH_LONG).show()
        }
    }

    /**
     *     Camera Activity
     */
    fun startCameraInvestigation(view: View) {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, CAMERA)
            }
        }
    }

    var photos: MutableList<Bitmap> = mutableListOf()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA && resultCode == RESULT_OK) {
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            photos.add(imageBitmap)

            val image1 = findViewById<View>(R.id.iimg1) as ImageView?
            val image2 = findViewById<View>(R.id.iimg2) as ImageView?


            image1!!.setImageBitmap(photos.get(0))
            if(photos.size == 2) {
                image2!!.setImageBitmap(photos.get(1))
            }
        }
    }
}
