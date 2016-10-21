package com.kamedon.boardgamemanager.domain.usecase

import android.graphics.Bitmap
import com.kamedon.boardgamemanager.infra.camera.CameraClient

/**
 * Created by kamei.hidetoshi on 2016/10/20.
 */
interface ICameraOnePreviewUserCase {
    fun preview(f: (Bitmap) -> Unit)
    fun release()
}

class CameraOnePreviewUserCase(val camera: CameraClient) : ICameraOnePreviewUserCase {
    override fun release() {
        camera.release()
    }

    override fun preview(f: (Bitmap) -> Unit) {
        CameraClient.autofocus { CameraClient.shoot(f) }
    }


}
