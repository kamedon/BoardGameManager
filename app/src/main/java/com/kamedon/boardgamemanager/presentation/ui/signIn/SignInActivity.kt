package com.kamedon.boardgamemanager.presentation.ui.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import com.kamedon.boardgamemanager.presentation.presenter.SignInPresenter
import com.kamedon.boardgamemanager.presentation.presenter.SignInView
import com.kamedon.boardgamemanager.presentation.ui.base.Page
import com.kamedon.boardgamemanager.presentation.ui.base.SecurityActivity
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.go

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
class SignInActivity : SecurityActivity(), SignInView {

    val RC_SIGN_IN = 1
    lateinit var presenter: SignInPresenter

    val progressView: View by lazy {
        findViewById(R.id.progress)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        presenter = SignInPresenter(this)
        di.inject(presenter)
        findViewById(R.id.btnSignIn).setOnClickListener {
            presenter.signIn()
        }
    }

    override fun onStop() {
        super.onStop()
        progressView.visibility = View.GONE
    }

    override fun show(intent: Intent) {
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun showProgress() {
        progressView.visibility = View.VISIBLE
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            presenter.signInResult(data)
        }
    }

    override fun createSecurityListener(): ISecureUseCase.Listener = object : ISecureUseCase.Listener {
        override fun onSignIn(user: User) {
            go(Page.BOARD_GAMES) {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            finish()
        }

        override fun onYetSignIn() {
        }

    }

    override fun logined() {
        go(Page.BOARD_GAMES) {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        finish()
    }

}
