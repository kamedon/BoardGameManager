package com.kamedon.boardgamemanager.presentation.ui.boardgame

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameListPresenter
import com.kamedon.boardgamemanager.presentation.presenter.BoardGameListView
import com.kamedon.boardgamemanager.util.extensions.di

class BoardGamesActivity : AppCompatActivity(), BoardGameListView {

    val list: RecyclerView by lazy {
        findViewById(R.id.list) as RecyclerView
    }

    lateinit var adapter: BoardGameAdapter
    lateinit var presenter: BoardGameListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_games)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }
        adapter = BoardGameAdapter(applicationContext)
        list.layoutManager = LinearLayoutManager(applicationContext);
        list.adapter = adapter
        (0..10).forEach {
            val item = BoardGame()
            item.name = "$it: game"
            adapter.add(item)
        }

        presenter = BoardGameListPresenter(this)
        di.inject(presenter)
    }

}
