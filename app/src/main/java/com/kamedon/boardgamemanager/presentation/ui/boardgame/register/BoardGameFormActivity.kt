package com.kamedon.boardgamemanager.presentation.ui.boardgame.register

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterPresenter
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameRegisterView
import com.kamedon.boardgamemanager.util.extensions.di

class BoardGameFormActivity : AppCompatActivity(), BoardGameRegisterView {

    lateinit var registerPresenter: BoardGameRegisterPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_register)
        registerPresenter = BoardGameRegisterPresenter(this)
        di.inject(registerPresenter)
    }
}
