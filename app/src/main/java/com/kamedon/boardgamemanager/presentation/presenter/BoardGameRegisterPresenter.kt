package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.domain.usecase.IBoardGameUseCase
import com.kamedon.boardgamemanager.domain.usecase.ISearchUseCase
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface BoardGameRegisterView {
    fun onSearchResult(it: BoardGame)
}

class BoardGameRegisterPresenter(val view: BoardGameRegisterView) {
    @Inject
    lateinit var useCase: IBoardGameUseCase

    @Inject
    lateinit var searchUseCase: ISearchUseCase

    fun searchBy(rx: LifecycleProvider<ActivityEvent>, keyword: String) = searchUseCase.searchByQRCode(keyword)?.apply {
        bindToLifecycle(rx)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it?.let { view.onSearchResult(it) } }

    }

    fun save(boardGame: BoardGame) {
        useCase.save(boardGame)
    }

}
