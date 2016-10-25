package com.kamedon.boardgamemanager.domain.entity

import com.google.firebase.database.Exclude

/**
 * Created by kamei.hidetoshi on 2016/10/22.
 */

class BoardGame {
    var key: String = ""
    var barcode: String = ""
    var name: String = ""
    var price: String = ""

    @Exclude
    fun toMap(): Map<String, Any> {
        val result = mutableMapOf<String, String>()
        result.put("key", key)
        result.put("barcode", barcode)
        result.put("name", name)
        result.put("price", price)
        return result
    }


}
