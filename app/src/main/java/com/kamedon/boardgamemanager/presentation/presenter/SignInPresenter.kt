package com.kamedon.boardgamemanager.presentation.presenter

import android.content.Intent
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.domain.usecase.ISignInUseCase
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
interface SignInView {
    fun show(signIn: Intent)
    fun showProgress()
    fun logined()
}

class SignInPresenter(val view: SignInView) {

    @Inject
    lateinit var signInUseCase: ISignInUseCase

    fun signIn() {
        view.show(signInUseCase.signIn())

    }

    fun signInResult(data: Intent?) {
        signInUseCase.result(data, object : ISignInUseCase.Listener {
            override fun onFail(e: Exception) {
            }

            override fun onComplete(user: User) {
                Timber.d(user.toString())
                view.logined()
            }
        })
    }

    fun logined(): Boolean = signInUseCase.logined()

}
