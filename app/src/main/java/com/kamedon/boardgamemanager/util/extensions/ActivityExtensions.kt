package com.kamedon.boardgamemanager.util.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.kamedon.boardgamemanager.KApplication
import com.kamedon.boardgamemanager.di.ApplicationComponent
import com.kamedon.boardgamemanager.presentation.ui.base.Page

/**
 * Created by kamei.hidetoshi on 2016/10/20.
 */

fun Context.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(this, message, duration).show()
}

fun Activity.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    application.toast(message, duration)
}

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    context.toast(message, duration)
}

val Fragment.di: ApplicationComponent
    get() = (activity.application as KApplication).di

val Activity.di: ApplicationComponent
    get() = (application as KApplication).di

inline fun Activity.go(page: Page, f: Intent.() -> Intent) = startActivity(f(page.intent(applicationContext)))
fun Activity.go(page: Page) = startActivity(page.intent(applicationContext))

inline fun Activity.goForResult(page: Page, requestId: Int, f: Intent.() -> Intent) = startActivityForResult(f(page.intent(applicationContext)), requestId)
fun Activity.goForResult(page: Page, requestId: Int) = startActivityForResult(page.intent(applicationContext), requestId)
