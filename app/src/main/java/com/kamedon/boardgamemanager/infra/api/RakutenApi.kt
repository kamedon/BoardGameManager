package com.kamedon.boardgamemanager.infra.api

import com.kamedon.boardgamemanager.BuildConfig
import com.kamedon.boardgamemanager.domain.entity.reponse.RakutenResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */

interface RakutenApi {
    @GET("/services/api/IchibaItem/Search/20140222")
    fun search(@Query("keyword") keyword: String, @Query("applicationId") applicationId: String = BuildConfig.RAKUTEN_API_KEY, @Query("format") format: String = "json"): Single<RakutenResponse>
}
