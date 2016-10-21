package com.kamedon.boardgamemanager.di

import com.google.android.gms.vision.barcode.BarcodeDetector
import com.kamedon.boardgamemanager.domain.usecase.BarcodeUseCase
import com.kamedon.boardgamemanager.domain.usecase.CameraOnePreviewUserCase
import com.kamedon.boardgamemanager.domain.usecase.IBarcodeUseCase
import com.kamedon.boardgamemanager.domain.usecase.ICameraOnePreviewUserCase
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Module
class PresentationModule() {

    @Provides
    @Singleton
    fun provideBarcodeUseCase(detector: BarcodeDetector): IBarcodeUseCase = BarcodeUseCase(detector)

    @Provides
    @Singleton
    fun provideOnePreviewUserCase(client: CameraClient): ICameraOnePreviewUserCase = CameraOnePreviewUserCase(client)

}
