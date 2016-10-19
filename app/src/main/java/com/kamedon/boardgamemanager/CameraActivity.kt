package com.kamedon.boardgamemanager

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector

class CameraActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        val txtView = findViewById(R.id.txtContent) as TextView
        val myImageView = findViewById(R.id.imgview) as ImageView
        val myBitmap = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.barcode)
        myImageView.setImageBitmap(myBitmap)

        val detector = BarcodeDetector.Builder(applicationContext).setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE).build()
        if (!detector.isOperational) {
            txtView.text = "Could not set up the detector!"
            return
        }

        findViewById(R.id.button).setOnClickListener {
            val frame = Frame.Builder().setBitmap(myBitmap).build()
            val barcodes = detector.detect(frame)

            val thisCode = barcodes.valueAt(0)
            txtView.text = thisCode.rawValue
        }


    }
}
