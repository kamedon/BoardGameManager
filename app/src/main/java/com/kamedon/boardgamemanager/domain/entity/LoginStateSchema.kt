package com.kamedon.boardgamemanager.domain.entity

import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
@Table(name = "login_state")
data class LoginStateSchema(
        @Key(name = "token") var token: String)
