package com.kamedon.boardgamemanager

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.btnCamera).setOnClickListener {
            startActivity(Intent(application, CameraActivity::class.java))
        }
    }
}
