package com.kamedon.boardgamemanager.presentation.ui.boardgame

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameListPresenter
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameListView
import com.kamedon.boardgamemanager.presentation.ui.base.Page
import com.kamedon.boardgamemanager.presentation.ui.base.SecurityActivity
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.go

class BoardGamesActivity : SecurityActivity(), BoardGameListView {

    val list: RecyclerView by lazy {
        findViewById(R.id.list) as RecyclerView
    }
    val btnRegister: View by lazy {
        findViewById(R.id.fab) as FloatingActionButton
    }

    lateinit var adapter: BoardGameAdapter
    lateinit var listPresenter: BoardGameListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_games)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        btnRegister.setOnClickListener { view ->
            go(Page.BOARD_GAME_FORM)
        }
        adapter = BoardGameAdapter(applicationContext)
        list.layoutManager = LinearLayoutManager(applicationContext);
        list.adapter = adapter
        (0..10).forEach {
            val item = BoardGame()
            item.name = "$it: game"
            adapter.add(item)
        }

        listPresenter = BoardGameListPresenter(this)
        di.inject(listPresenter)

    }

}
