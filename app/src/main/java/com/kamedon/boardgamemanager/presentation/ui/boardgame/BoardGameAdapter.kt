package com.kamedon.boardgamemanager.presentation.ui.boardgame

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.presentation.view.RecyclerArrayAdapter

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */

class BoardGameAdapter(context: Context) : RecyclerArrayAdapter<BoardGame, BoardGameViewHolder>() {
    private val inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: BoardGameViewHolder, position: Int) {
        holder.nameText.text = data[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardGameViewHolder {
        return BoardGameViewHolder(inflater.inflate(BoardGameViewHolder.LAYOUT_ID, parent, false));
    }

}
