package uz.mobiler.ppdgram.models

class GroupMassage {

    var massages: String? = null
    var date: String? = null
    var fromUid: String? = null
    var photoUrl:String?=null

    constructor()

    constructor(massages: String?, date: String?, fromUid: String?, photoUri: String?) {
        this.massages = massages
        this.date = date
        this.fromUid = fromUid
        this.photoUrl = photoUri
    }
}