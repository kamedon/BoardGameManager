package com.kamedon.boardgamemanager.domain.entity.reponse

import java.io.Serializable

/**
 * Created by kamei.hidetoshi on 2016/10/24.
 */
data class RakutenResponse(val count: Int, val Items: List<RakutenItems>) : Serializable {

}

data class RakutenItems(val Item: RakutenItem) : Serializable {

}

data class RakutenItem(val itemName: String) : Serializable {

}
