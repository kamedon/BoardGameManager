package com.kamedon.boardgamemanager.domain.usecase

import android.content.Intent
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kamedon.boardgamemanager.domain.entity.User
import com.kamedon.boardgamemanager.infra.repository.ILoginRepository
import com.kamedon.boardgamemanager.util.extensions.toUser

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
interface ISignInUseCase {
    fun logined(): Boolean
    fun signIn(): Intent
    fun result(data: Intent?, listener: Listener)

    interface Listener {
        fun onFail(e: Exception)
        fun onComplete(user: User)
    }

}


class SignInUseCase(val auth: FirebaseAuth, val client: GoogleApiClient, val repository: ILoginRepository) : ISignInUseCase {

    override fun logined() = repository.logined()
    override fun signIn() = Auth.GoogleSignInApi.getSignInIntent(client)
    override fun result(data: Intent?, listener: ISignInUseCase.Listener) {
        val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
        if (result.isSuccess) {
            val account = result.signInAccount
            firebaseAuthWithGoogle(account, listener)
        } else {
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?, listener: ISignInUseCase.Listener) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        auth.signInWithCredential(credential)
                .addOnFailureListener { listener.onFail(it) }
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        var user = it.result.user.toUser()
                        repository.signIn(user)
                        listener.onComplete(user)
                    } else {
//                        listener.onCancel(it)
                    }
                }
    }
}
