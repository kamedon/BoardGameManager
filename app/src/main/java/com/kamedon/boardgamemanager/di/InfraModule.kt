package com.kamedon.boardgamemanager.di

import android.content.Context
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class InfraModule() {

    @Provides
    @Singleton
    fun provideBarcodeDetector(context: Context): BarcodeDetector = BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.ALL_FORMATS).build()

    @Provides
    @Singleton
    fun provideCameraClient(): CameraClient = CameraClient

}
