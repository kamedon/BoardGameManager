package com.kamedon.boardgamemanager.domain.usecase

import com.kamedon.boardgamemanager.infra.repository.IBoardGameRepository

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface IBoardGameUseCase

class BoardGameUseCase(val repository: IBoardGameRepository) : IBoardGameUseCase {

}
