package com.kamedon.boardgamemanager.infra.camera

import android.graphics.*
import android.hardware.Camera
import java.io.ByteArrayOutputStream

/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */

object CameraClient {
    val INVALID_CAMERA_ID = -1
    var mCameraFacingBackId = INVALID_CAMERA_ID
    var mCameraFacingFrontId = INVALID_CAMERA_ID
    var mDefaultCameraId = INVALID_CAMERA_ID
    var mCurrentCameraId = INVALID_CAMERA_ID
    var focusing = false
    var camera: Camera? = null

    init {
        val numberOfCameras = Camera.getNumberOfCameras()
        val cameraInfo = Camera.CameraInfo()
        for (cameraId in 0..numberOfCameras - 1) {
            Camera.getCameraInfo(cameraId, cameraInfo)
            when (cameraInfo.facing) {
                Camera.CameraInfo.CAMERA_FACING_BACK -> if (INVALID_CAMERA_ID === mCameraFacingBackId) {
                    mCameraFacingBackId = cameraId
                    mDefaultCameraId = cameraId
                }
                Camera.CameraInfo.CAMERA_FACING_FRONT -> if (INVALID_CAMERA_ID === mCameraFacingFrontId) {
                    mCameraFacingFrontId = cameraId
                }
            }
            mCurrentCameraId = if (mDefaultCameraId === INVALID_CAMERA_ID) mCameraFacingBackId else mDefaultCameraId
        }

        setup()
    }

    fun open(): Camera {
        return open(mCurrentCameraId)
    }

    fun open(id: Int): Camera {
        val camera = Camera.open(id)
        mCurrentCameraId = id
        camera.setDisplayOrientation(90)
        return camera
    }


    fun release() {
        camera?.apply {
            setPreviewCallback(null)
            release()
            camera = null
        }
        focusing = false
    }

    fun autofocus(callback: () -> Unit) {
        if (!focusing) {
            synchronized(this) {
                if (camera == null) {
                    return
                }
                if (!focusing) {
                    focusing = true
                    try {
                        camera?.autoFocus { success: Boolean, c: Camera ->
                            callback()
                            focusing = false
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        focusing = false
                    }
                }
            }
        }
    }

    fun shoot(f: (Bitmap) -> Unit) {
        camera?.setOneShotPreviewCallback { bytes: ByteArray, camera: Camera ->
            val previewWidth = camera.parameters.previewSize.width
            val previewHeight = camera.parameters.previewSize.height
            f(bytesToBitmap(bytes, previewWidth, previewHeight))
        }
    }

    fun bytesToBitmap(bytes: ByteArray, width: Int, height: Int): Bitmap {
        val yuvimage = YuvImage(bytes, ImageFormat.NV21, width, height, null)
        val baos = ByteArrayOutputStream()
        yuvimage.compressToJpeg(Rect(0, 0, width, height), 80, baos)
        val jdata = baos.toByteArray()
        val bitmapFatoryOptions = BitmapFactory.Options()
        bitmapFatoryOptions.inPreferredConfig = Bitmap.Config.RGB_565
        val bmp = BitmapFactory.decodeByteArray(jdata, 0, jdata.size, bitmapFatoryOptions)
        return bmp.copy(Bitmap.Config.ARGB_8888, true)
    }

    fun setup(): Camera {
        val c = open()
        c.parameters.focusMode = autofoucsMode(c)
        camera = c
        return c
    }

    private fun autofoucsMode(camera: Camera): String {
        return if (camera.parameters.supportedFocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            return Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        } else {
            return Camera.Parameters.FOCUS_MODE_AUTO
        }
    }


}