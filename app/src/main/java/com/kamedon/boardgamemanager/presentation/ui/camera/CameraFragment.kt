package com.kamedon.boardgamemanager.presentation.ui.camera

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.gms.vision.barcode.Barcode
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.presenter.BarcodePresenter
import com.kamedon.boardgamemanager.presentation.presenter.IBarcodeView
import com.kamedon.boardgamemanager.presentation.ui.base.RequestKey
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.toast
import com.trello.rxlifecycle2.components.support.RxFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */
class CameraFragment : RxFragment(), IBarcodeView {

    companion object {
        val KEY_FOR_REQUEST = "key_for_request"

        fun newInstance(forRequest: Boolean): Fragment {
            var fragment = CameraFragment()
            var bundle = Bundle()
            bundle.putBoolean(KEY_FOR_REQUEST, forRequest)
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var presenter: BarcodePresenter
    lateinit var image: ImageView
    var forRequest: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forRequest = arguments.getBoolean(KEY_FOR_REQUEST)
        presenter = BarcodePresenter(this)
        di.inject(presenter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, null)
        image = view.findViewById(R.id.image) as ImageView
        return view
    }


    private var  shootSubscription: Disposable? = null

    override fun onResume() {
        super.onResume()
        shootSubscription = presenter.loopOfShoot()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .bindToLifecycle(this).subscribe()
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
                    shootSubscription?.dispose()
                    if (forRequest) {
                        var intent = Intent()
                        intent.putExtra(RequestKey.BUNDLE_KEY_BARCODE, it.rawValue)
                        activity.setResult(Activity.RESULT_OK, intent)
                        activity.finish()
                    }
                }
    }


}