package com.kamedon.boardgamemanager.presentation.ui.boardgame.register

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterPresenter
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterView
import com.kamedon.boardgamemanager.presentation.ui.base.Page
import com.kamedon.boardgamemanager.presentation.ui.base.RequestKey
import com.kamedon.boardgamemanager.presentation.ui.base.SecurityActivity
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.goForResult

class BoardGameFormActivity : SecurityActivity(), BoardGameRegisterView {

    lateinit var presenter: BoardGameRegisterPresenter

    val btnBarcode: View by lazy {
        findViewById(R.id.btnBarcode)
    }

    val editBarcode: EditText by lazy {
        findViewById(R.id.editBarcode) as EditText
    }

    val editName: EditText by lazy {
        findViewById(R.id.editName) as EditText
    }

    val btnSubmit: View by lazy {
        findViewById(R.id.btnSubmit)
    }

    val btnSearch: View by lazy {
        findViewById(R.id.btnSearch)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_register)
        presenter = BoardGameRegisterPresenter(this)
        di.inject(presenter)
        btnBarcode.setOnClickListener {
            goForResult(Page.CAMERA, RequestKey.REQUEST_BARCODE)
        }

        btnSubmit.setOnClickListener {
            val game: BoardGame = BoardGame()
            game.barcode = editBarcode.text.toString()
            game.name = editName.text.toString()
            presenter.save(game)
            finish()
        }
        btnSearch.setOnClickListener {
            if (!TextUtils.isEmpty(editName.text.toString())) {
                presenter.searchBy(this@BoardGameFormActivity, editName.text.toString())
            }

        }
    }

    override fun onSearchResult(boardGame: BoardGame) {
        editName.setText(boardGame.name)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            RequestKey.REQUEST_BARCODE -> if (resultCode == Activity.RESULT_OK) {
                data?.getStringExtra("barcode")?.let {
                    editBarcode.setText(it)
                    presenter.searchBy(this@BoardGameFormActivity, it)
                    return
                }
            }

        }

        super.onActivityResult(requestCode, resultCode, data)
    }
}

