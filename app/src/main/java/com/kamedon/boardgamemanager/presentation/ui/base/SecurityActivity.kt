package com.kamedon.boardgamemanager.presentation.ui.base

import android.os.Bundle
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import com.kamedon.boardgamemanager.presentation.presenter.SecurePresenter
import com.kamedon.boardgamemanager.presentation.presenter.SecureView
import com.kamedon.boardgamemanager.util.extensions.di
import com.kamedon.boardgamemanager.util.extensions.go
import com.trello.rxlifecycle.components.support.RxAppCompatActivity

/**
 * Created by kamei.hidetoshi on 2016/10/23.
 */
open abstract class SecurityActivity : RxAppCompatActivity(), SecureView {

    lateinit private var securePresenter: SecurePresenter
    lateinit private var listener: ISecureUseCase.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        securePresenter = SecurePresenter(this)
        di.inject(securePresenter)
    }


    override fun onStart() {
        super.onStart()
        listener = createSecurityListener()
        securePresenter.onStart(listener)
    }


    open fun createSecurityListener(): ISecureUseCase.Listener = object : ISecureUseCase.Listener {
        override fun onSignIn(user: User) {
        }

        override fun onYetSignIn() {
            go(Page.SIGN_IN)
        }
    }

    override fun onStop() {
        super.onStop()
        securePresenter.onStop()
    }

    fun signOut() {
        securePresenter.signOut()
    }

}
