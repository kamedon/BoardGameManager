package com.kamedon.boardgamemanager.presentation.ui.boardgame

import android.content.Intent
import android.os.Bundle
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import com.kamedon.boardgamemanager.presentation.ui.base.SecurityActivity
import com.kamedon.boardgamemanager.presentation.ui.signIn.SignInActivity

class BoardGameListActivity : SecurityActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_game_list)
    }

    override fun createSecurityListener(): ISecureUseCase.Listener = object : ISecureUseCase.Listener {
        override fun onSignIn(user: User) {
        }

        override fun onYetSignIn() {
            startActivity(Intent(applicationContext, SignInActivity::class.java))
            finish()
        }
    }
}
