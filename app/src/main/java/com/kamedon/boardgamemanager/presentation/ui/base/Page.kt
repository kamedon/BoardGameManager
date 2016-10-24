package com.kamedon.boardgamemanager.presentation.ui.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import com.kamedon.boardgamemanager.R
import com.kamedon.boardgamemanager.presentation.ui.boardgame.BoardGamesActivity
import com.kamedon.boardgamemanager.presentation.ui.boardgame.register.BoardGameFormActivity
import com.kamedon.boardgamemanager.presentation.ui.camera.CameraActivity
import com.kamedon.boardgamemanager.presentation.ui.signIn.SignInActivity

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
enum class Page(val nameId: Int, val page: Class<out Activity>) {
    SIGN_IN(R.string.page_sign_in, SignInActivity::class.java) {
        override fun intent(context: Context): Intent {
            return super.intent(context).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
        }
    },
    BOARD_GAMES(R.string.page_borad_games, BoardGamesActivity::class.java),
    BOARD_GAME_FORM(R.string.page_borad_game_form, BoardGameFormActivity::class.java),

    CAMERA(R.string.page_camera, CameraActivity::class.java);

    open fun intent(context: Context) = Intent(context, page)
    fun name(resources: Resources) = resources.getString(nameId)
}
