package com.kamedon.boardgamemanager.di

import com.kamedon.boardgamemanager.presentation.presenter.BarcodePresenter
import dagger.Component
import javax.inject.Singleton

/**
 * Created by kamei.hidetoshi on 2016/10/21.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class, InfraModule::class, PresentationModule::class))
interface ApplicationComponent {
    fun inject(presenter: BarcodePresenter)
}