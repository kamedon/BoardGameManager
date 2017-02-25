package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.usecase.IBoardGameUseCase
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */

interface BoardGameListView

class BoardGameListPresenter(val view: BoardGameListView) {
    @Inject
    lateinit var useCase: IBoardGameUseCase

}
