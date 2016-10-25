package com.kamedon.boardgamemanager.domain.entity

import java.io.Serializable

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
data class User(var id: String = "", var name: String? = "", var email: String? = "") : Serializable {

}
