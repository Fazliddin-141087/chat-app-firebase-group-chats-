package uz.mobiler.ppdgram.models

import java.io.Serializable

class Massages : Serializable {

    var massages: String? = null
    var date: String? = null
    var fromUid: String? = null
    var toUid: String? = null

    constructor(massages: String?, date: String?, fromUid: String?, toUid: String?) {
        this.massages = massages
        this.date = date
        this.fromUid = fromUid
        this.toUid = toUid
    }

    constructor()
}