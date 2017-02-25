package com.kamedon.boardgamemanager.domain.usecase

import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.infra.repository.IBoardGameRepository

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
interface IBoardGameUseCase {
    fun save(boardGame: BoardGame)
}

class BoardGameUseCase(val repository: IBoardGameRepository) : IBoardGameUseCase {
    override fun save(boardGame: BoardGame) {
        repository.save(boardGame)
    }

}
