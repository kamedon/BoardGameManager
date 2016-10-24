package com.kamedon.boardgamemanager.presentation.ui.boardgame.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterPresenter
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterView
import com.kamedon.boardgamemanager.presentation.ui.base.Page
import com.kamedon.boardgamemanager.presentation.ui.base.RequestKey
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.goForResult

class BoardGameFormActivity : AppCompatActivity(), BoardGameRegisterView {

    lateinit var registerPresenter: BoardGameRegisterPresenter

    val btnBarcode: View by lazy {
        findViewById(R.id.btnBarcode)
    }

    val editBarcode: EditText by lazy {
        findViewById(R.id.editBarcode) as EditText
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_register)
        registerPresenter = BoardGameRegisterPresenter(this)
        di.inject(registerPresenter)
        btnBarcode.setOnClickListener {
            goForResult(Page.CAMERA, RequestKey.REQUEST_BARCODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {

            RequestKey.REQUEST_BARCODE -> if (resultCode == Activity.RESULT_OK) {
                data?.getStringExtra("barcode")?.let {
                    editBarcode.setText(it)
                }
                return
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}
