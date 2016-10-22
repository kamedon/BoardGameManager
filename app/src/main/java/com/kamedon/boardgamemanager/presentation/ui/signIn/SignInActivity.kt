package com.kamedon.boardgamemanager.presentation.ui.signIn

import android.os.Bundle
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.presenter.SignInPresenter
import com.kamedon.boardgamemanager.presentation.presenter.SignInView
import com.kamedon.boardgamemanager.util.extensions.di
import com.trello.rxlifecycle.components.RxActivity

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
class SignInActivity : RxActivity(), SignInView {

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

}
