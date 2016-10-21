package com.kamedon.boardgamemanager.domain.usecase

import android.content.Context
import android.graphics.Bitmap
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import rx.Observable
import rx.lang.kotlin.toObservable

/**
 * Created by kamei.hidetoshi on 2016/10/20.
 */
interface IBarcodeUseCase {
    fun read(bitmap: Bitmap): Observable<Barcode>
}

class BarcodeUseCase(context: Context) : IBarcodeUseCase {

    private val detector: BarcodeDetector by lazy {
        BarcodeDetector.Builder(context).setBarcodeFormats(Barcode.ALL_FORMATS).build()
    }

    override fun read(bitmap: Bitmap): Observable<Barcode> {
        if (detector.isOperational) {
            val frame = Frame.Builder().setBitmap(bitmap).build()
            val result = detector.detect(frame)
            (0..result.size()).toObservable().map {
                result[it]
            }
        }
        return Observable.empty()
    }

}
