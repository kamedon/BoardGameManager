package com.kamedon.boardgamemanager.presentation.presenter

import com.google.android.gms.vision.barcode.Barcode
import com.kamedon.boardgamemanager.domain.usecase.IBarcodeUseCase
import com.kamedon.boardgamemanager.domain.usecase.ICameraOnePreviewUserCase
import rx.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */

interface IBarcodeView {
    fun read(read: Observable<Barcode>)
}

class BarcodePresenter(val view: IBarcodeView) {
    @Inject
    lateinit var barcodeUseCase: IBarcodeUseCase

    @Inject
    lateinit var cameraPreview: ICameraOnePreviewUserCase

    fun pause() {
        cameraPreview.release()
    }

    fun loopOfShoot(repeatSec: Long = 3) = Observable.just(1)
            .map {
                cameraPreview.preview { view.read((barcodeUseCase.read(it))) }
            }
            .repeatWhen {
                it.delay(repeatSec, TimeUnit.SECONDS)
            }

}
