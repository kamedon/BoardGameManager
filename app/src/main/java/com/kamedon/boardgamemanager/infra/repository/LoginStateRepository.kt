package com.kamedon.boardgamemanager.infra.repository

import com.kamedon.boardgamemanager.domain.entity.User
import com.rejasupotaro.android.kvs.annotations.Key
import com.rejasupotaro.android.kvs.annotations.Table

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */
interface ILoginRepository {
    fun me(): User
    fun logined(): Boolean
    fun signIn(user: User)
    fun signOut()
}

class LoginRepository(val prefs: LoginPrefs) : ILoginRepository {
    override fun logined(): Boolean = prefs.hasId()
    override fun me(): User = User(prefs.id, prefs.name, prefs.email)

    override fun signIn(user: User) {
        prefs.putId(user.id)
        prefs.putName(user.name)
        prefs.putEmail(user.email)
    }

    override fun signOut() {
        prefs.clear()
    }

}

@Table(name = "login")
class LoginPrefsSchema(
        @Key(name = "id") var id: String,
        @Key(name = "name") var name: String,
        @Key(name = "email") var email: String
)


