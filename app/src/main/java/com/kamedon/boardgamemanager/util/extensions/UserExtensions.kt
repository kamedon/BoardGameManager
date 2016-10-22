package com.kamedon.boardgamemanager.util.extensions

import com.google.firebase.auth.FirebaseUser
import com.kamedon.boardgamemanager.domain.entity.User

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
fun FirebaseUser.toUser() = User(uid, displayName, email)
