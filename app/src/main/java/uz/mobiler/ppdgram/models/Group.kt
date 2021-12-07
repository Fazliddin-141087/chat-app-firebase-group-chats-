package uz.mobiler.ppdgram.models

import java.io.Serializable

class Group : Serializable {

    var keys: String? = null
    var userList:ArrayList<ChekUser>?=null
    var groupName: String? = null
    var positionUser: String? = null

    constructor()

    constructor(userList: ArrayList<ChekUser>?, groupName: String?) {
        this.userList = userList
        this.groupName = groupName
    }

    constructor(keys: String?, userList: ArrayList<ChekUser>?, groupName: String?) {
        this.keys = keys
        this.userList = userList
        this.groupName = groupName
    }

    constructor(userList: ArrayList<ChekUser>?, groupName: String?, positionUser: String?) {
        this.userList = userList
        this.groupName = groupName
        this.positionUser = positionUser
    }

}