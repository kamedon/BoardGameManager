package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.usecase.IBoardGameUseCase
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface BoardGameRegisterView {
}

class BoardGameRegisterPresenter(val view: BoardGameRegisterView) {
    @Inject
    lateinit var useCase: IBoardGameUseCase

}
