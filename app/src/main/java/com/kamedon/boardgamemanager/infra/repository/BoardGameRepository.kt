package com.kamedon.boardgamemanager.infra.repository

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.kamedon.boardgamemanager.domain.entity.BoardGame

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface IBoardGameRepository {
    fun save(boardGame: BoardGame)
    fun find(page: Int)
    fun release()
}

class BoardGameRepository(val database: FirebaseDatabase) : IBoardGameRepository {
    companion object {
        val TABLE = "boardgames"
        val PAGE_LIMIT = 100
    }

    override fun save(boardGame: BoardGame) {
        val reference = database.reference
        val key = reference.child(TABLE).push().key
        val childUpdates = mapOf("/$TABLE/$key" to boardGame.toMap())
        reference.updateChildren(childUpdates)
    }

    override fun find(page: Int) {
        val reference = database.reference
        val query = reference.child(TABLE).orderByKey()
        query.addChildEventListener(object : ChildEventListener {
            override fun onChildMoved(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildChanged(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildAdded(p0: DataSnapshot?, p1: String?) {
            }

            override fun onChildRemoved(p0: DataSnapshot?) {
            }

            override fun onCancelled(p0: DatabaseError?) {
            }
        })
    }

    override fun release() {

    }

}
