package com.kamedon.boardgamemanager

import android.app.Application
import com.facebook.stetho.Stetho
import com.kamedon.boardgamemanager.di.*
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
class KApplication : Application() {

    lateinit var di: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this);
        }
        di = DaggerApplicationComponent.builder()
                .appModule(AppModule(this))
                .infraModule(InfraModule())
                .presentationModule(PresentationModule())
                .build()

    }

}

