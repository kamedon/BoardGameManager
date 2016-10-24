package com.kamedon.boardgamemanager.infra.repository

import com.kamedon.boardgamemanager.domain.entity.reponse.RakutenResponse
import com.kamedon.boardgamemanager.infra.api.RakutenApi
import rx.Observable

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */

interface IRakutenRepository {
    fun search(keyword: String): Observable<RakutenResponse>
}

class RakutenRepository(val api: RakutenApi) : IRakutenRepository {

    override fun search(keyword: String) = api.search(keyword)
}
