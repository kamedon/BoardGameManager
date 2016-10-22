package com.kamedon.boardgamemanager.domain.entity

import java.io.Serializable

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
data class User(val id: String, val name: String?, val email: String?) : Serializable
