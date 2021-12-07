package uz.mobiler.ppdgram.models

import java.io.Serializable

class User:Serializable {

    var displayName:String?=null
    var email:String?=null
    var photoUrl:String?=null
    var uid:String?=null
    var online:Boolean?=null
    var onCheck:Boolean?=null


    constructor()

    constructor(
        displayName: String?,
        email: String?,
        photoUrl: String?,
        uid: String?,
        online: Boolean?,
        onCheck: Boolean?
    ) {
        this.displayName = displayName
        this.email = email
        this.photoUrl = photoUrl
        this.uid = uid
        this.online = online
        this.onCheck = onCheck
    }
}