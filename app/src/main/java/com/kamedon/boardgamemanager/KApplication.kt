package com.kamedon.boardgamemanager

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
class KApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }

}

