package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.usecase.ISecureUseCase
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/23.
 */
interface SecureView {

}

class SecurePresenter(val view: SecureView) {

    @Inject
    lateinit var secureUseCase: ISecureUseCase

    fun onStart(listener: ISecureUseCase.Listener) {
        secureUseCase.onStart(listener)
    }

    fun onStop() {
        secureUseCase.onStop()
    }

    fun signOut() {
        secureUseCase.signOut()
    }


}
