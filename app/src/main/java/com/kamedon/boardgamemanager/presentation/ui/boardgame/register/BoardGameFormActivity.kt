package com.kamedon.boardgamemanager.presentation.ui.boardgame.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_register)
        registerPresenter = BoardGameRegisterPresenter(this)
        di.inject(registerPresenter)
        btnBarcode.setOnClickListener {
            goForResult(Page.CAMERA, RequestKey.REQUEST_BARCODE)
        }
    }
}
