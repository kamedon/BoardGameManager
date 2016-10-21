package com.kamedon.boardgamemanager.presentation.ui.camera

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.kamedon.boardgamemanager.KApplication
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.usecase.IBarcodeUseCase
import com.kamedon.boardgamemanager.domain.usecase.ICameraOnePreviewUserCase
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import com.kamedon.boardgamemanager.util.extensions.toast
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */
class CameraFragment : RxFragment() {

    @Inject
    lateinit var barcodeUseCase: IBarcodeUseCase

    @Inject
    lateinit var cameraPreview: ICameraOnePreviewUserCase

    companion object {
        fun newInstance() = CameraFragment()
    }

    lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.application as KApplication).di.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, null)
        image = view.findViewById(R.id.image) as ImageView
        return view
    }

    private var shootSubscription: Subscription? = null

    override fun onResume() {
        super.onResume()

        shootSubscription = shoot()

    }

    fun shoot() = Observable.just(1)
            .bindToLifecycle(this)
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                cameraPreview.preview { callback(it) }
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

    val callback = { bitmap: Bitmap ->
        barcodeUseCase.read(bitmap)
                .bindToLifecycle(this@CameraFragment)
                .take(1)
                .subscribe {
                    toast(it.rawValue)
                    shootSubscription?.unsubscribe()
                }
    }

}