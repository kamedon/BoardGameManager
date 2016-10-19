package com.kamedon.boardgamemanager.presentation.ui.camera

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.infra.camera.CameraClient


/**
 * Created by kamei.hidetoshi on 2016/10/19.
 */
class CameraFragment : Fragment() {

    companion object {
        fun newInstance() = CameraFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_camera, null)
        return view
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        CameraClient.release()
    }

}