package com.kamedon.boardgamemanager.presentation.view

import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.ViewGroup
import com.kamedon.boardgamemanager.infra.camera.CameraClient
import java.io.IOException

/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */

// TODO: CameraViewもDaggerで差し込めるようにしたい
class CameraView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ViewGroup(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private val mSurfaceView: SurfaceView

    private var mHolder: SurfaceHolder
    private var mPictureSize: Camera.Size? = null
    private var mSupportedPictureSizes: MutableList<Camera.Size>? = null
    private var mSupportedPreviewSizes: MutableList<Camera.Size>? = null
    var mPreviewSize: Camera.Size? = null

    init {

        mSurfaceView = SurfaceView(context)
        addView(mSurfaceView)
        mHolder = mSurfaceView.holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = resolveSize(suggestedMinimumWidth, widthMeasureSpec)
        val height = resolveSize(suggestedMinimumHeight, heightMeasureSpec)
        setMeasuredDimension(width, height)

        if (!isInEditMode) {
            setOptimalSize(height, width)
        }
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        if (childCount > 0) {
            val child = getChildAt(0)

            val width = right - left
            val height = bottom - top

            var previewWidth = height
            var previewHeight = width
            mPreviewSize?.let {
                previewWidth = it.height
                previewHeight = it.width
            }
            // Center the child SurfaceView within the parent.
            if (width * previewHeight > height * previewWidth) {
                val scaledChildWidth = previewWidth * height / previewHeight
                child.layout((width - scaledChildWidth) / 2, 0, (width + scaledChildWidth) / 2, height)
            } else {
                val scaledChildHeight = previewHeight * width / previewWidth
                child.layout(0, (height - scaledChildHeight) / 2, width, (height + scaledChildHeight) / 2)
            }
        }
        startPreview()
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        if (!isInEditMode) {
            try {
                var camera = CameraClient.camera
                if (camera == null) {
                    camera = CameraClient.setup()
                }
                camera.setPreviewDisplay(holder)
            } catch (e: IOException) {
                e.printStackTrace()
//                Log.e(TAG, e.getClass().getName() + ": caused by setPreviewDisplay at Preview#surfaceCreated()", e)
            }

        }
        requestLayout()
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        CameraClient.camera?.stopPreview()
    }

    fun startPreview() {

        CameraClient.camera?.apply {
            if (mPreviewSize != null) {
                var params = parameters;
                params.setPreviewSize(mPreviewSize!!.width, mPreviewSize!!.height)
                requestLayout()
                parameters = params
                startPreview()
            }

        }
    }


    /**
     * 最適な画像サイズを設定する

     * @param width
     * *
     * @param height
     */
    private fun setOptimalSize(width: Int, height: Int) {
        mPreviewSize = getOptimalPreviewSize(width, height)
        mPictureSize = getOptimalPictureSize(width, height)
    }


    /**
     * 最適なプレビューサイズを取得する

     * @param width
     * *
     * @param height
     * *
     * @return
     */
    private fun getOptimalPreviewSize(width: Int, height: Int): Camera.Size? {
        val aspectTolerance = 0.1
        val targetRatio = width.toDouble() / height
        if (mSupportedPreviewSizes == null) {
            CameraClient.camera?.let {
                mSupportedPreviewSizes = it.parameters.supportedPreviewSizes
                if (mSupportedPreviewSizes == null) {
                    return null
                }
            }
        }

        var optimalSize: Camera.Size? = null
        var minDiff = java.lang.Double.MAX_VALUE
        val targetHeight = height
        mSupportedPreviewSizes?.let {


            // 比率が許容範囲内なものを探す
            for (size in it) {
                val ratio = size.width.toDouble() / size.height
                if (Math.abs(ratio - targetRatio) > aspectTolerance) {
                    continue
                }
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - targetHeight).toDouble()
                }
            }

            // 許容範囲内のものが見つからなかったら、許容範囲に一番近いものを探す
            if (optimalSize == null) {
                minDiff = java.lang.Double.MAX_VALUE
                for (size in it) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size
                        minDiff = Math.abs(size.height - targetHeight).toDouble()
                    }
                }
            }
            return optimalSize
        }
        return null
    }


    /**
     * 最適な画像サイズを取得する

     * @param width
     * *
     * @param height
     * *
     * @return
     */
    private fun getOptimalPictureSize(width: Int, height: Int): Camera.Size? {
        val aspectTolerance = 0.1
        val targetRatio = width.toDouble() / height
        if (mSupportedPictureSizes == null) {
            CameraClient.camera?.let {
                mSupportedPictureSizes = it.parameters.supportedPictureSizes
                if (mSupportedPictureSizes == null) {
                    return null
                }
            }
        }

        var optimalSize: Camera.Size? = null
        var minDiff = java.lang.Double.MAX_VALUE
        val targetHeight = height

        // 比率が許容範囲内なものを探す
        mSupportedPictureSizes?.let {

            for (size in it) {
                val ratio = size.width.toDouble() / size.height
                if (Math.abs(ratio - targetRatio) > aspectTolerance) {
                    continue
                }
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size
                    minDiff = Math.abs(size.height - targetHeight).toDouble()
                }
            }

            // 許容範囲内のものが見つからなかったら、許容範囲に一番近いものを探す
            if (optimalSize == null) {
                minDiff = java.lang.Double.MAX_VALUE
                for (size in it) {
                    if (Math.abs(size.height - targetHeight) < minDiff) {
                        optimalSize = size
                        minDiff = Math.abs(size.height - targetHeight).toDouble()
                    }
                }
            }
            return optimalSize
        }
        return null
    }

}
