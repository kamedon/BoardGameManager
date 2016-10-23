package com.kamedon.boardgamemanager.presentation.ui.signIn

import android.content.Intent
import android.os.Bundle
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import com.kamedon.boardgamemanager.presentation.presenter.SignInPresenter
import com.kamedon.boardgamemanager.presentation.presenter.SignInView
import com.kamedon.boardgamemanager.presentation.ui.base.SecurityActivity
import com.kamedon.boardgamemanager.presentation.ui.boardgame.BoardGameListActivity
import com.kamedon.boardgamemanager.util.extensions.di

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
class SignInActivity : SecurityActivity(), SignInView {

    val RC_SIGN_IN = 1
    lateinit var presenter: SignInPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter(this)
        di.inject(presenter)
        findViewById(R.id.btnSignIn).setOnClickListener {
            presenter.signIn()
        }
    }

    override fun show(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun showProgress() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            presenter.signInResult(data)
        }
    }

    override fun createSecurityListener(): ISecureUseCase.Listener = object : ISecureUseCase.Listener {
        override fun onSignIn(user: User) {
            go()
        }

        override fun onYetSignIn() {
        }

    }

    override fun logined() {
        go()
    }

    private fun go() {
        val intent = Intent(applicationContext, BoardGameListActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

}
