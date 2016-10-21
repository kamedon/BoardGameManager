package com.kamedon.boardgamemanager.presentation.ui.camera

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.vision.barcode.Barcode
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.presenter.BarcodePresenter
import com.kamedon.boardgamemanager.presentation.presenter.IBarcodeView
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.toast
import com.trello.rxlifecycle.components.support.RxFragment
import com.trello.rxlifecycle.kotlin.bindToLifecycle
import rx.Observable
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */
class CameraFragment : RxFragment(), IBarcodeView {

    companion object {
        fun newInstance() = CameraFragment()
    }

    lateinit var presenter: BarcodePresenter
    lateinit var image: ImageView
    private var shootSubscription: Subscription? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = BarcodePresenter(this)
        di.inject(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, null)
        image = view.findViewById(R.id.image) as ImageView
        return view
    }


    override fun onResume() {
        super.onResume()
        shootSubscription = presenter.loopOfShoot()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this).subscribe { }
    }

    override fun onPause() {
        super.onPause()
        presenter.pause()
    }

    override fun read(o: Observable<Barcode>) {
        o.bindToLifecycle(this)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .take(1)
                .subscribe {
                    toast(it.rawValue)
                    shootSubscription?.unsubscribe()
                }
    }


}