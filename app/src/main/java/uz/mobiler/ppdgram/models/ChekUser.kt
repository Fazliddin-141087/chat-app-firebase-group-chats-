package uz.mobiler.ppdgram.models

import java.io.Serializable

class ChekUser :Serializable {

    var uid:String?=null
    var photoUrl:String?=null
    var positionUser:Int?=null

    constructor(uid: String?, photoUrl: String?, positionUser: Int?) {
        this.uid = uid
        this.photoUrl = photoUrl
        this.positionUser = positionUser
    }

    constructor()
}