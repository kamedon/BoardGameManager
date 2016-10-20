package com.kamedon.boardgamemanager.presentation.ui.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import com.kamedon.boardgamemanager.util.extensions.toast
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */
class CameraFragment : RxFragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    val detector: BarcodeDetector by lazy {
        BarcodeDetector.Builder(activity.application).setBarcodeFormats(Barcode.ALL_FORMATS).build()
    }

    lateinit var image: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, null)
        image = view.findViewById(R.id.image) as ImageView
        return view
    }

    override fun onResume() {
        super.onResume()
        CameraClient.shootSubject
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this)
                .subscribe(object : Observer<Bitmap> {
                    override fun onCompleted() {
                    }

                    override fun onNext(bitmap: Bitmap) {
                        bitmap.let {
                            Log.d("shoot", "bitmap")
                            if (detector.isOperational) {
                                val frame = Frame.Builder().setBitmap(bitmap).build()

                                val barcodes = detector.detect(frame)
                                if (barcodes.size() > 0) {
                                    val thisCode = barcodes.valueAt(0)
                                    toast(thisCode.toString())
                                }
                                toast("none barcode")
                                image.setImageBitmap(bitmap)
                            }
                        }
                        Log.d("shoot", "finish");
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }

                })

        shoot()

    }

    fun shoot() = Observable.just(1)
            .bindToLifecycle(this)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                CameraClient.autofocus { CameraClient.shoot() }
            }
            .repeatWhen {
                it.delay(3, TimeUnit.SECONDS)
            }
            .subscribe {
                Log.d("shoot", "start");
            }


    override fun onPause() {
        super.onPause()
        CameraClient.release()
    }

}