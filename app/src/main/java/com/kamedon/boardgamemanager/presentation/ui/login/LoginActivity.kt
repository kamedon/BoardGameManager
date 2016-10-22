package com.kamedon.boardgamemanager.presentation.ui.login

import android.os.Bundle
import com.kamedon.boardgamemanager.R
import com.trello.rxlifecycle.components.RxActivity

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
class LoginActivity : RxActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

}
