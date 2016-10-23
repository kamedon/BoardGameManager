package com.kamedon.boardgamemanager.presentation.ui.base

import android.os.Bundle
import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import com.kamedon.boardgamemanager.presentation.presenter.SecurePresenter
import com.kamedon.boardgamemanager.presentation.presenter.SecureView
import com.kamedon.boardgamemanager.util.extensions.di
import com.trello.rxlifecycle.components.RxActivity

/**
 * Created by kamei.hidetoshi on 2016/10/23.
 */
open abstract class SecurityActivity : RxActivity(), SecureView {

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

    abstract fun createSecurityListener(): ISecureUseCase.Listener

    override fun onStop() {
        super.onStop()
        securePresenter.onStop()
    }

    fun signOut() {
        securePresenter.signOut()
    }

}
