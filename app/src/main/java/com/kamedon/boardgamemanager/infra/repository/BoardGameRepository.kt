package com.kamedon.boardgamemanager.infra.repository

import com.google.firebase.database.FirebaseDatabase
import com.kamedon.boardgamemanager.domain.entity.BoardGame

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface IBoardGameRepository {
    fun save(boardGame: BoardGame)
}

class BoardGameRepository(val database: FirebaseDatabase) : IBoardGameRepository {
    companion object {
        val TABLE = "boardgames"
    }

    override fun save(boardGame: BoardGame) {
        val reference = database.reference
        val key = reference.child(TABLE).push().key
        val childUpdates = mapOf("/$TABLE/$key" to boardGame.toMap())
        reference.updateChildren(childUpdates)
    }

}
