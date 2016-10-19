package com.kamedon.boardgamemanager.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.ui.camera.CameraActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.btnCamera).setOnClickListener {
            startActivity(Intent(application, CameraActivity::class.java))
        }
    }
}
