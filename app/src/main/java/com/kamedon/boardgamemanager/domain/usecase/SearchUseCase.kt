package com.kamedon.boardgamemanager.domain.usecase

import com.kamedon.boardgamemanager.domain.entity.BoardGame
import com.kamedon.boardgamemanager.infra.repository.IRakutenRepository
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by kamei.hidetoshi on 2017/02/25.
 */
interface ISearchUseCase {
    fun searchByKeyword(keyword: String): Maybe<BoardGame>?
    fun searchByQRCode(keyword: String): Maybe<BoardGame>?
}

class SearchUseCase @Inject constructor(val repository: IRakutenRepository) : ISearchUseCase {

    override fun searchByKeyword(keyword: String) = repository.search(keyword).filter { it.Items.count() > 0 }
            .map {
                it.Items[0].Item.let {
                    return@map BoardGame().apply {
                        name = it.itemName
                        price = it.itemPrice.toString()
                    }
                }
            }

    override fun searchByQRCode(qrCode: String) = searchByKeyword(qrCode)


}
