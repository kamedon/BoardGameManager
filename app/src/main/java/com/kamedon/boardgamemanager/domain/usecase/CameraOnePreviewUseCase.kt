package com.kamedon.boardgamemanager.domain.usecase

import android.graphics.Bitmap
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import rx.subjects.Subject

/**
 * Created by kamei.hidetoshi on 2016/10/20.
 */
interface ICameraOnePreviewUserCase {
    fun preview(f: (Subject<Bitmap, Bitmap>) -> Unit)
}

class CameraOnePreviewUserCase(val camera: CameraClient) : ICameraOnePreviewUserCase {

    override fun preview(f: (Subject<Bitmap, Bitmap>) -> Unit) {
        CameraClient.shootSubject.apply {
            f(this)
        }
        CameraClient.autofocus { CameraClient.shoot() }
    }


}
