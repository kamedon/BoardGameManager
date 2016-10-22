package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.usecase.ISignInUseCase
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
interface SignInView

class SignInPresenter(val view: SignInView) {

    @Inject
    lateinit var signInUseCase: ISignInUseCase

    fun signIn() {
    }

}
