package com.kamedon.boardgamemanager.di

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class AppModule(val application: Application) {

    @Provides
    @Singleton
    fun provideApplicationContext() = application.applicationContext

}
