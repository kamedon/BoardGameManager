package com.kamedon.boardgamemanager.domain.usecase

import com.google.firebase.auth.FirebaseAuth
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.infra.repository.ILoginRepository
import com.kamedon.boardgamemanager.util.extensions.toUser

/**
 * Created by kamei.hidetoshi on 2016/10/23.
 */

interface ISecureUseCase {
    fun onStart(listener: Listener)
    fun onStop()

    interface Listener {
        fun onSignIn(user: User)
        fun onYetSignIn()
    }

}

class SecureUseCase(val auth: FirebaseAuth, val repository: ILoginRepository) : ISecureUseCase {

    lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onStart(listener: ISecureUseCase.Listener) {
        authStateListener = FirebaseAuth.AuthStateListener { it ->
            it.currentUser?.let {
                var user = it.toUser()
                repository.signIn(user)
                listener.onSignIn(user)
                return@AuthStateListener
            }
            listener.onYetSignIn()
        }
        auth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        auth.removeAuthStateListener(authStateListener)
    }
}
