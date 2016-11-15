package com.kamedon.boardgamemanager.presentation.ui.camera

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.kamedon.boardgamemanager.R
import permissions.dispatcher.*

@RuntimePermissions
class CameraActivity : AppCompatActivity() {

    private var forRequest: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        forRequest = intent != null
        CameraActivityPermissionsDispatcher.cameraNeedsPermissionWithCheck(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // NOTE: delegate the permission handling to generated method
        CameraActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults)
    }


    @NeedsPermission(Manifest.permission.CAMERA)
    fun cameraNeedsPermission() {
        // NOTE: Perform action that requires the permission. If this is run by PermissionsDispatcher, the permission will have been granted
        Handler().postDelayed({ showCamera() }, 500L)
    }

    fun showCamera() {
        supportFragmentManager.beginTransaction().replace(R.id.fragment, CameraFragment.newInstance(forRequest)).commit()
    }


    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        // NOTE: Show a rationale to explain why the permission is needed, e.g. with a dialog.
        // Call proceed() or cancel() on the provided PermissionRequest to continue or abort
        showRationaleDialog(R.string.permission_camera_rationale, request)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        // NOTE: Deal with a denied permission, e.g. by showing specific UI
        // or disabling certain functionality
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        Toast.makeText(this, R.string.permission_camera_never_askagain, Toast.LENGTH_SHORT).show()
    }

    private fun showRationaleDialog(messageResId: Int, request: PermissionRequest) {
        AlertDialog.Builder(this)
                .setPositiveButton(android.R.string.ok, { dialog, which -> request.proceed() })
                .setNegativeButton(android.R.string.cancel, { dialog, which -> request.cancel() })
                .setCancelable(false).setMessage(messageResId).show()
    }

}
