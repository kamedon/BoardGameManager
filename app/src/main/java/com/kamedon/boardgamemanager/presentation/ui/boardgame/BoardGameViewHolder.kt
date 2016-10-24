package com.kamedon.boardgamemanager.presentation.ui.boardgame

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.kamedon.boardgamemanager.R

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */

class BoardGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        val LAYOUT_ID = R.layout.list_boardgame
    }

    val nameText: TextView

    init {
        nameText = itemView.findViewById(R.id.textTitle) as TextView
    }
}