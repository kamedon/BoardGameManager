package com.kamedon.boardgamemanager.presentation.presenter

import com.kamedon.boardgamemanager.domain.usecase.IBoardGameUseCase
import com.kamedon.boardgamemanager.infra.repository.IRakutenRepository
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface BoardGameRegisterView {
}

class BoardGameRegisterPresenter(val view: BoardGameRegisterView) {
    @Inject
    lateinit var useCase: IBoardGameUseCase

    @Inject
    lateinit var repository: IRakutenRepository

    fun searchBy(keyword: String) = repository.search(keyword)
            .filter {
                it.count > 0
            }
            .map {
                it.Items[0].Item
            }


}
