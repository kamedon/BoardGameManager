package com.kamedon.boardgamemanager.domain.usecase

import android.graphics.Bitmap
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import io.reactivex.Observable
import timber.log.Timber

/**
 * Created by kamei.hidetoshi on 2016/10/20.
 */
interface IBarcodeUseCase {
    fun read(bitmap: Bitmap): Observable<Barcode>
}

class BarcodeUseCase(val detector: BarcodeDetector) : IBarcodeUseCase {

    override fun read(bitmap: Bitmap): Observable<Barcode> {
        if (detector.isOperational) {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val result = detector.detect(frame)
            Timber.d("range: ${result.size()}")
            if (result.size() > 0) {
                return Observable.range(0, result.size() - 1).map {
                    result.valueAt(it)
                }
            }
        }
        return Observable.empty()
    }

}
